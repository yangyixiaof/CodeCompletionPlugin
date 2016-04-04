package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.ArrayUtil;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class argumentList implements OneCode{
	
	private List<referedExpression> el = new LinkedList<referedExpression>();
	
	public argumentList() {
	}
	
	public void AddToFirst(referedExpression re)
	{
		getEl().add(0, re);
	}
	
	public void AddReferedExpression(referedExpression re)
	{
		getEl().add(re);
	}

	public List<referedExpression> getEl() {
		return el;
	}

	public void setEl(List<referedExpression> el) {
		this.el = el;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argumentList)
		{
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			int maxsize = Math.max(size, tsize);
			if (maxsize <= 2)
			{
				return true;
			}
			int minsize = Math.min(size, tsize);
			if (Math.abs(size-tsize) > minsize)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argumentList)
		{
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			if (Math.abs(size-tsize) <= 1)
			{
				return 1;
			}
			else
			{
				return SimilarityHelper.ComputeTwoIntegerSimilarity(size, tsize);
			}
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		
		// must be in reverse order.
		expected.add(null);
		boolean conflict = false;
		referedExpression invokerhint = el.get(0);
		CSNode invcn = new CSNode(CSNodeType.TempUsed);
		conflict = invokerhint.HandleCodeSynthesis(squeue, expected, handler, invcn, ai);
		List<CSNode> paramsnode = new LinkedList<CSNode>();
		Iterator<referedExpression> itr = el.iterator();
		itr.next();
		while (itr.hasNext())
		{
			referedExpression re = itr.next();
			CSNode oparam = new CSNode(CSNodeType.TempUsed);
			conflict = re.HandleCodeSynthesis(squeue, expected, handler, oparam, ai);
			if (conflict)
			{
				return true;
			}
			paramsnode.add(oparam);
		}
		Map<String, TypeCheck> resdatas = new TreeMap<String, TypeCheck>();
		Map<String, TypeCheck> datas = invcn.getDatas();
		Set<String> precodes = datas.keySet();
		Iterator<String> pcitr = precodes.iterator();
		while (pcitr.hasNext())
		{
			String pc = pcitr.next();
			StringBuilder sb = new StringBuilder(pc);
			sb.append("(");
			TypeCheck retandparamstypes = datas.get(pc);
			if (retandparamstypes == null)
			{
				// directly add param.
				Iterator<CSNode> pitr = paramsnode.iterator();
				while (pitr.hasNext())
				{
					CSNode pcn = pitr.next();
					sb.append(pcn.GetFirstDataWithoutTypeCheck());
					if (pitr.hasNext())
					{
						sb.append(",");
					}
				}
			}
			else
			{
				List<Boolean> usedparams = ArrayUtil.InitialBooleanArray(paramsnode.size());
				List<Class<?>> tps = retandparamstypes.getExpargstypesclasses();
				Iterator<Class<?>> tpitr = tps.iterator();
				while (tpitr.hasNext())
				{
					Class<?> c = tpitr.next();
					String ct = HandleOneClassParamNodes(c, paramsnode, usedparams);
					sb.append(ct);
					if (tpitr.hasNext())
					{
						sb.append(",");
					}
				}
			}
			sb.append(")");
			resdatas.put(sb.toString(), retandparamstypes);
		}
		result.setDatas(resdatas);
		expected.pop();
		return false;
	}
	
	private String HandleOneClassParamNodes(Class<?> c, List<CSNode> paramsnode, List<Boolean> usedparams)
	{
		Iterator<CSNode> pitr = paramsnode.iterator();
		int usedidx = 0;
		String unusedorlatestused = null;
		while (pitr.hasNext())
		{
			CSNode pcn = pitr.next();
			String select = null;
			Map<String, TypeCheck> dts = pcn.getDatas();
			Set<String> codes = dts.keySet();
			Iterator<String> codeitr = codes.iterator();
			while (codeitr.hasNext())
			{
				String code = codeitr.next();
				TypeCheck codecheck = dts.get(code);
				Class<?> rtclass = codecheck.getExpreturntypeclass();
				if (TypeCheckHelper.CanBeMutualCast(c, rtclass))
				{
					select = code;
					break;
				}
			}
			if (select == null)
			{
				select = codes.iterator().next();
			}
			if (!usedparams.get(usedidx))
			{
				usedparams.set(usedidx, true);
				return select;
			}
			else
			{
				unusedorlatestused = select;
			}
			usedidx++;
		}
		return unusedorlatestused;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}