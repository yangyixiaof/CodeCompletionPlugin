package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class ListHelper {
	
	public static List<FlowLineNode<CSFlowLineData>> TranslateToMethodDataNode(List<FlowLineNode<CSFlowLineData>> alls, boolean hasem)
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = alls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			result.add(new FlowLineNode<CSFlowLineData>(new CSMethodInvocationData(hasem, fln.getData()), fln.getProbability()));
		}
		return result;
	}
	
	public static List<Boolean> InitialBooleanArray(int size)
	{
		List<Boolean> result = new LinkedList<Boolean>();
		for (int i=0;i<size;i++)
		{
			result.add(false);
		}
		return result;
	}
	
	public static void PrintList(ArrayList<String> result)
	{
		System.out.println("Element Begin:");
		Iterator<String> itr = result.iterator();
		while (itr.hasNext())
		{
			String ele = itr.next();
			System.out.println("Element:" + ele);
		}
		System.out.println("Element End.");
	}
	
	public static String ConcatJoinLast(int size, List<Sentence> analysislist) {
		StringBuffer sb = new StringBuffer("");
		int end = analysislist.size() - 1;
		int start = end + 1 - size;
		Iterator<Sentence> itr = analysislist.iterator();
		for (int i=0;i<start;i++)
		{
			itr.next();
		}
		for (int i = start; i <= end; i++) {
			Sentence sete = itr.next();
			String split = " ";
			if (i == end) {
				split = "";
			}
			sb.append(sete.getSentence() + split);
		}
		return sb.toString().trim();
	}
	
	public static String ConcatJoin(List<Sentence> analysislist) {
		StringBuffer sb = new StringBuffer("");
		Iterator<Sentence> itr = analysislist.iterator();
		while (itr.hasNext()) {
			Sentence sete = itr.next();
			String split = " ";
			if (!itr.hasNext()) {
				split = "";
			}
			sb.append(sete.getSentence() + split);
		}
		return sb.toString().trim();
	}
	
}