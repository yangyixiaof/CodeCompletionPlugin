package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSDataMetaInfo;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderExtraInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class CSFlowLineHelper {
	
	/**
	 * Warning: this function only operate raw data string, please notice the
	 * connect information.
	 * 
	 * @param prefix
	 * @param one
	 * @param postfix
	 */
	public static List<FlowLineNode<CSFlowLineData>> ConcateOneFLStamp(String prefix, List<FlowLineNode<CSFlowLineData>> one, String postfix) {
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData dt = fln.getData();
			String cnt = dt.getData();
			cnt = (prefix == null ? "" : prefix) + cnt + (postfix == null ? "" : postfix);
			dt.setData(cnt);
		}
		return one;
	}
	
	public static void AddToEveryParNodeExtraInfo(List<FlowLineNode<CSFlowLineData>> one, String key, Object info)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			fln.getData().getExtraData().AddExtraData(key, info);
		}
	}
	
	public static void AddToEveryRexpParNodeExtraVariableHolderInfo(List<FlowLineNode<CSFlowLineData>> one, String varname)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData flndata = fln.getData();
			flndata.getExtraData().AddExtraData(CSDataMetaInfo.VariableHolders, new CSVariableHolderExtraInfo(varname, flndata.getClass()));
		}
	}
	
	public List<FlowLineNode<CSFlowLineData>> ForwardMerge(String prefix, List<FlowLineNode<CSFlowLineData>> one, String concator, List<FlowLineNode<CSFlowLineData>> two, String postfix, CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		if (one.size() == 0) {
			if (two == null || two.size() == 0) {
				return null;
			} else {
				if (prefix != null && !prefix.equals(""))
				{
					// testing
					System.err.println("Warning: concate two list and one is null but prefix is not empty.");
					return null;
				}
				else
				{
					CheckConcator(concator);
					return CSFlowLineHelper.ConcateOneFLStamp(concator, two, postfix);
				}
			}
		} else {
			if (two == null || two.size() == 0) {
				if (postfix != null && !postfix.equals(""))
				{
					// testing
					System.err.println("Warning: concate two list and one is null but prefix is not empty.");
					return null;
				}
				else
				{
					CheckConcator(concator);
					return CSFlowLineHelper.ConcateOneFLStamp(prefix, one, concator);
				}
			} else {
				List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
				Iterator<FlowLineNode<CSFlowLineData>> itr1 = one.iterator();
				while (itr1.hasNext()) {
					FlowLineNode<CSFlowLineData> fln1 = itr1.next();
					Iterator<FlowLineNode<CSFlowLineData>> itr2 = two.iterator();
					while (itr2.hasNext()) {
						FlowLineNode<CSFlowLineData> fln2 = itr2.next();
						FlowLineNode<CSFlowLineData> tmp = null;
						try {
							
							tmp = ConcateTwoFlowLineNode(prefix, fln1, concator, fln2, postfix, squeue, smthandler,
									 oneafter, beforetwo);
						} catch (TypeConflictException e) {
							e.printStackTrace();
							continue;
						}
						result.add(tmp);
					}
				}
				if (result.size() == 0) {
					return null;
				}
				return result;
			}
		}
	}
	
	private static void CheckConcator(String concator) {
		if (concator != null && !concator.equals("")) {
			System.err
					.println("No another part, but the concator has real values. Serious error, the system will exit.");
			System.exit(1);
		}
	}
	
	public static FlowLineNode<CSFlowLineData> ConcateTwoFlowLineNode(String prefix, FlowLineNode<CSFlowLineData> one,
			String concator, FlowLineNode<CSFlowLineData> two, String postfix, 
			CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		CSFlowLineData d1 = one.getData();
		CSFlowLineData d2 = two.getData();
		CSFlowLineData data = d1.Merge(prefix, concator, d2, postfix, squeue, smthandler, oneafter, beforetwo);
		double cnctprob = one.getProbability() + two.getProbability();
		FlowLineNode<CSFlowLineData> cncted = new FlowLineNode<CSFlowLineData>(data, cnctprob);
		return cncted;
	}
	
}