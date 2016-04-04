package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class CSFlowLineHelper {
	
	// only for inner concatenate in one statement.
	public static List<FlowLineNode<CSFlowLineData>> ConcateTwoFlowLineNodes(String prefix, List<FlowLineNode<CSFlowLineData>> flnsone, String concator, List<FlowLineNode<CSFlowLineData>> flnstwo, String postfix, TypeComputationKind tck, Integer id, Sentence sete, Integer structsignal)
	{
		if (flnsone == null || flnsone.size() == 0)
		{
			if (flnstwo == null || flnstwo.size() == 0)
			{
				return null;
			}
			else
			{
				CheckConcator(concator);
				return flnstwo;
			}
		}
		else
		{
			if (flnstwo == null || flnstwo.size() == 0)
			{
				CheckConcator(concator);
				return flnsone;
			}
			else
			{
				List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
				Iterator<FlowLineNode<CSFlowLineData>> itr1 = flnsone.iterator();
				while (itr1.hasNext())
				{
					FlowLineNode<CSFlowLineData> fln1 = itr1.next();
					CSFlowLineData d1 = fln1.getData();
					String str1 = d1.getData();
					Iterator<FlowLineNode<CSFlowLineData>> itr2 = flnstwo.iterator();
					while (itr2.hasNext())
					{
						FlowLineNode<CSFlowLineData> fln2 = itr2.next();
						CSFlowLineData d2 = fln2.getData();
						String str2 = d2.getData();
						Class<?> clz = null;
						try {
							clz = TypeComputer.ComputeType(d1.getDcls(), d2.getDcls(), tck);
						} catch (TypeConflictException e) {
							continue;
						}
						String cnctcnt = (prefix == null ? "" : prefix) + str1 + (concator == null ? "" : concator) + str2 + (postfix == null ? "" : postfix);
						double cnctprob = fln1.getProbability() + fln2.getProbability();
						FlowLineNode<CSFlowLineData> cncted = new FlowLineNode<CSFlowLineData>(new CSFlowLineData(id, sete, cnctcnt, structsignal, clz, d2.isHashole(), d1.getHandler()), cnctprob);
						result.add(cncted);
					}
				}
				if (result.size() == 0)
				{
					return null;
				}
				return result;
			}
		}
	}
	
	private static void CheckConcator(String concator)
	{
		if (concator != null && !concator.equals(""))
		{
			System.err.println("No another part, but the concator has real values. Serious error, the system will exit.");
			System.exit(1);
		}
	}
	
	/**
	 * Warning: this function only operate raw data string, please notice the connect information.
	 * @param prefix
	 * @param one
	 * @param postfix
	 */
	public static void ConcateOneFlowLineNodes(String prefix, List<FlowLineNode<CSFlowLineData>> one, String postfix)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData dt = fln.getData();
			String cnt = dt.getData();
			cnt = (prefix == null ? "" : prefix) + cnt + (postfix == null ? "" : postfix);
			dt.setData(cnt);
		}
	}
	
}