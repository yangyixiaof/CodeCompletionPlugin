package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.ListDynamicHeper;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class classOrInterfaceType extends type{
	
	List<type> tps = null;
	
	public classOrInterfaceType(List<type> result) {
		this.tps = result;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((classOrInterfaceType) t).tps);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((classOrInterfaceType) t).tps);
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		Iterator<type> itr = tps.iterator();
		while (itr.hasNext())
		{
			type t = itr.next();
			CSNode ttp = new CSNode(CSNodeType.TempUsed);
			boolean conflict = t.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
			if (conflict)
			{
				return true;
			}
			result.setDatas(CSNodeHelper.ConcatTwoNodesDatas(ttp, result, ".", -1));
		}
		CSNodeHelper.HandleTypeByTypeCodes(result);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = null;
		List<type> reveredtps = new ListDynamicHeper<type>().ReverseList(tps);
		Iterator<type> itr = reveredtps.iterator();
		while (itr.hasNext())
		{
			type tp = itr.next();
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			if (result == null)
			{
				result = tpls;
			}
			else
			{
				if (result.size() == 0)
				{
					break;
				}
				result = CodeSynthesisHelper.HandleTypeSpecificationInfer(result, tpls, squeue, smthandler);
			}
		}
		if (result == null || result.size() == 0)
		{
			return CodeSynthesisHelper.HandleMultipleConcateType(squeue, smthandler, tps, ".");
		}
		return result;
	}
	
}