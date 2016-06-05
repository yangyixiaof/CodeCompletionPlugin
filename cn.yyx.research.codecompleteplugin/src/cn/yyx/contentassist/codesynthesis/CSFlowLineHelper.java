package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.data.CSExtraProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;

public class CSFlowLineHelper {
	
	/**
	 * Warning: this function only operate raw data string, please notice the
	 * connect information.
	 * 
	 * @param prefix
	 * @param one
	 * @param postfix
	 */
	public static List<FlowLineNode<CSFlowLineData>> ConcateOneFlowLineList(String prefix, List<FlowLineNode<CSFlowLineData>> one, String postfix) {
		if (one == null || one.size() == 0)
		{
			return null;
		}
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
	
	public static List<FlowLineNode<CSFlowLineData>> ForwardConcate(String prefix, List<FlowLineNode<CSFlowLineData>> one, String concator, List<FlowLineNode<CSFlowLineData>> two, String postfix, CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind tck) throws CodeSynthesisException {
		if (one == null || one.size() == 0) {
			return null;
		} else {
			if (two == null || two.size() == 0) {
				return null;
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
							TypeComputationKind tckc = tck == null ? null : (TypeComputationKind)tck.clone();
							tmp = ConcateTwoFlowLineNode(prefix, fln1, concator, fln2, postfix, squeue, smthandler,
									tckc);
							ConcateBlockStart(tmp, fln1, fln2);
							ConcateExtraProperty(tmp, fln1, fln2);
						} catch (TypeConflictException e) {
							// e.printStackTrace();
							System.err.println(e.getMessage());
							continue;
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							System.exit(1);
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
	
	public static FlowLineNode<CSFlowLineData> ConcateTwoFlowLineNode(String prefix, FlowLineNode<CSFlowLineData> one,
			String concator, FlowLineNode<CSFlowLineData> two, String postfix, 
			CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind tck) throws CodeSynthesisException {
		CSFlowLineData d1 = one.getData();
		CSFlowLineData d2 = two.getData();
		CSFlowLineData data = d1.Merge(prefix, concator, d2, postfix, squeue, smthandler, tck);
		double cnctprob = MergeTwoDataProbability(d1.getSete(), one.getProbability(), d2.getSete(), two.getProbability());
		FlowLineNode<CSFlowLineData> cncted = new FlowLineNode<CSFlowLineData>(data, cnctprob);
		return cncted;
	}
	
	public static double MergeTwoDataProbability(Sentence sete1, double prob1, Sentence sete2, double prob2)
	{
		if (sete1 == sete2)
		{
			return prob1;
		}
		else
		{
			return prob1 + prob2;
		}
	}
	
	private static void ConcateExtraProperty(FlowLineNode<CSFlowLineData> tmp, FlowLineNode<CSFlowLineData> fln1,
			FlowLineNode<CSFlowLineData> fln2) {
		CSFlowLineData d1 = fln1.getData();
		CSFlowLineData d2 = fln2.getData();
		CSExtraProperty cs1 = d1.getCsep();
		CSExtraProperty cs2 = d2.getCsep();
		if (cs1 != null && cs2 != null)
		{
			System.err.println("Important error! two nodes all have block start, what the fuck.");
			new Exception("Important error! two nodes all have block start, what the fuck.").printStackTrace();
			System.exit(1);
		}
		if (cs1 == null && cs2 != null)
		{
			tmp.getData().setCsep(cs2);
		}
		if (cs1 != null && cs2 == null)
		{
			tmp.getData().setCsep(cs1);
		}
	}
	
	private static void ConcateBlockStart(FlowLineNode<CSFlowLineData> ftmp, FlowLineNode<CSFlowLineData> fln1, FlowLineNode<CSFlowLineData> fln2)
	{
		CSFlowLineData d1 = fln1.getData();
		CSFlowLineData d2 = fln2.getData();
		FlowLineNode<CSFlowLineData> bs1 = d1.getSynthesisCodeManager().getBlockstart();
		String bsi1 = d1.getSynthesisCodeManager().getBlocktostartid();
		FlowLineNode<CSFlowLineData> bs2 = d2.getSynthesisCodeManager().getBlockstart();
		String bsi2 = d2.getSynthesisCodeManager().getBlocktostartid();
		if (bs1 != null && bs2 != null)
		{
			// choose the most head one.
			/*FlowLineNode<CSFlowLineData> selected = null;
			String selectedid = null;
			if (IsParent(bs1, bs2))
			{
				selected = bs2;
				selectedid = bsi2;
			}
			if (IsParent(bs2, bs1))
			{
				selected = bs1;
				selectedid = bsi1;
			}
			if (selected == null)
			{
				System.err.println("No parent. Serious Error. The system should stop.");
				System.exit(1);
			}
			ftmp.getData().getSynthesisCodeManager().setBlockstart(selected, selectedid);*/
			System.err.println("Important error! two nodes all have block start, what the fuck.");
			new Exception("Important error! two nodes all have block start, what the fuck.").printStackTrace();
			System.exit(1);
		}
		if (bs1 == null && bs2 != null)
		{
			ftmp.getData().getSynthesisCodeManager().setBlockstart(bs2, bsi2);
			ftmp.getData().setId(d2.getId());
			bs2.getData().getSynthesisCodeManager().AddSynthesisCode(bsi2, ftmp);
			// do need to set tck?
		}
		if (bs1 != null && bs2 == null)
		{
			ftmp.getData().getSynthesisCodeManager().setBlockstart(bs1, bsi1);
			ftmp.getData().setId(d1.getId());
			bs1.getData().getSynthesisCodeManager().AddSynthesisCode(bsi1, ftmp);
			// do need to set tck?
		}
	}
	
	/*private static boolean IsParent(FlowLineNode<CSFlowLineData> child, FlowLineNode<CSFlowLineData> parent)
	{
		FlowLineNode<CSFlowLineData> tmp = child;
		while (tmp != null)
		{
			if (tmp == parent)
			{
				return true;
			}
			tmp = tmp.getPrev();
		}
		return false;
	}*/
	
}