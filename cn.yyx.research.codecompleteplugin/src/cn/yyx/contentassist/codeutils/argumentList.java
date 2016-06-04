package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSDataMetaInfo;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationProperty;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementFirstArgHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.ListDynamicHeper;
import cn.yyx.contentassist.commonutils.ListHelper;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class argumentList implements OneCode {

	private List<referedExpression> el = new LinkedList<referedExpression>();
	private firstArg fa = null;

	public argumentList() {
	}

	public void AddToFirst(referedExpression re) {
		getEl().add(0, re);
	}

	public void AddReferedExpression(referedExpression re) {
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
		if (t instanceof argumentList) {
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			int maxsize = Math.max(size, tsize);
			if (maxsize <= 2) {
				return true;
			}
			int minsize = Math.min(size, tsize);
			if (Math.abs(size - tsize) > minsize) {
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argumentList) {
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			if (Math.abs(size - tsize) <= 1) {
				return 1;
			} else {
				return SimilarityHelper.ComputeTwoIntegerSimilarity(size, tsize);
			}
		}
		return 0;
	}
	
	public List<FlowLineNode<CSFlowLineData>> HandleMethodIntegrationCodeSynthesis(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String methodname, boolean hasem) throws CodeSynthesisException {
		CheckUtil.CheckStatementHandlerIsSpecialKind(smthandler, CSMethodStatementHandler.class);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		// realhandler.setArgsize(el.size() - 1);
		// change to reverse order list.
		List<referedExpression> reverseel = new ListDynamicHeper<referedExpression>().ReverseList(el);
		LinkedList<List<FlowLineNode<CSFlowLineData>>> positiveargs = new LinkedList<List<FlowLineNode<CSFlowLineData>>>();
		Iterator<referedExpression> ritr = reverseel.iterator();
		Map<String, MethodTypeSignature> mts = new TreeMap<String, MethodTypeSignature>();
		while (ritr.hasNext()) {
			referedExpression re = ritr.next();
			// List<FlowLineNode<CSFlowLineData>> oneargpospossibles = ;
			// if (!ritr.hasNext()) {
				List<FlowLineNode<CSFlowLineData>> rels = re.HandleCodeSynthesis(squeue, smthandler);
				if (rels == null || rels.size() == 0) {
					return null;
				}
				positiveargs.add(0, rels);
			// }
		}
		// handle invoker.
		List<FlowLineNode<CSFlowLineData>> results = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> invokers = fa.HandleClassOrMethodInvoke(squeue,
				new CSMethodStatementFirstArgHandler(realhandler), methodname, mts);
		if (invokers == null || invokers.size() == 0)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> itr = invokers.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData data = fln.getData();
			MethodTypeSignature msig = mts.get(data.getId());
			StringBuilder sb = new StringBuilder(data.getData());
			if (msig == null) {
				if (TypeCheckHelper.IsInferredType(data.getDcls())) {
					Iterator<List<FlowLineNode<CSFlowLineData>>> pitr = positiveargs.iterator();
					sb.append("(");
					while (pitr.hasNext()) {
						List<FlowLineNode<CSFlowLineData>> pcnls = pitr.next();
						sb.append(pcnls.get(0).getData().getData());
						if (pitr.hasNext()) {
							sb.append(",");
						}
					}
					sb.append(")");
				}
			} else {
				// msig != null.
				// check and add argument.
				// List<Boolean> usedparams =
				// ListHelper.InitialBooleanArray(positiveargs.size());
				List<LinkedList<CCType>> tps = msig.getArgtypes();
				if (tps.size() != positiveargs.size() && !msig.isLastHasVarArg()) {
					continue;
				}
				Iterator<LinkedList<CCType>> tpitr = tps.iterator();
				Iterator<List<FlowLineNode<CSFlowLineData>>> pitr = positiveargs.iterator();
				sb.append("(");
				try {
					LinkedList<CCType> lastc = null;
					while (pitr.hasNext()) {
						LinkedList<CCType> c = null;
						if (tpitr.hasNext()) {
							c  = tpitr.next();
						} else {
							c = lastc;
						}
						if (!tpitr.hasNext())
						{
							lastc = c;
						}
						if (c == null)
						{
							new Exception("Logic error of program writer.").printStackTrace();
							System.exit(1);
						}
						List<FlowLineNode<CSFlowLineData>> parg = pitr.next();
						String ct = HandleOneParam(c, parg);
						sb.append(ct);
						if (pitr.hasNext()) {
							sb.append(",");
						}
					}
					/*while (tpitr.hasNext()) {
						LinkedList<CCType> c = tpitr.next();
						List<FlowLineNode<CSFlowLineData>> parg = pitr.next();
						// String ct = HandleOneClassParamNodes(c, positiveargs,
						// usedparams);
						String ct = HandleOneParam(c, parg);
						sb.append(ct);
						if (tpitr.hasNext()) {
							sb.append(",");
						}
					}*/
				} catch (CodeSynthesisException e) {
					continue;
				}
				sb.append(")");
			}
			data.setData(sb.toString());
			
			if (!ListHelper.DetailContains(results, data))
			{
				results.add(new FlowLineNode<CSFlowLineData>(data, fln.getProbability()));
			}
			
			// Stack<Integer> signals = new Stack<Integer>();
			// signals.add(DataStructureSignalMetaInfo.MethodInvocation);
			FlowLineNode<CSFlowLineData> mf = realhandler.getMostfar();
			// realhandler.getMostfar();
			if (mf != null) {
				String id = CSFlowLineBackTraceGenerationHelper.GetConcateId(squeue.getLast(), mf) + "." + data.getId();
				mf.getData().getSynthesisCodeManager().AddSynthesisCode(id, fln);
				data.getExtraData().AddExtraData(CSDataMetaInfo.LastNode, data);
				data.getSynthesisCodeManager().setBlockstart(mf, id);
				data.setCsep(new CSMethodInvocationProperty(hasem, null));
				data.setTck(null);
			}
		}
		return results;
	}

	public String HandleOneParam(LinkedList<CCType> c, List<FlowLineNode<CSFlowLineData>> parg)
			throws CodeSynthesisException {
		Iterator<FlowLineNode<CSFlowLineData>> pitr = parg.iterator();
		while (pitr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = pitr.next();
			CSFlowLineData flndata = fln.getData();
			if (TypeCheckHelper.CanBeMutualCast(c, flndata.getDcls())) {
				return flndata.getData();
			}
		}
		throw new CodeSynthesisException("Argument and param type are not equal.");
	}

	@Override
	@Deprecated
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck(
				"argumentList should not invoke HandleCodeSynthesis, should invoke HandleMethodIntegrationCodeSynthesis instead.");
		return null;
	}

	public firstArg getFirstArgument() {
		return fa;
	}

	public void setFirstArgument(firstArg fa) {
		this.fa = fa;
	}

}