package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineHelper {
	
	public static List<FlowLineNode<CSFlowLineData>> ConcateTwoFlowLineNodes(String prefix, List<FlowLineNode<CSFlowLineData>> flnsone, String concator, List<FlowLineNode<CSFlowLineData>> flnstwo, String postfix)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr1 = flnsone.iterator();
		while (itr1.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln1 = itr1.next();
			Iterator<FlowLineNode<CSFlowLineData>> itr2 = flnstwo.iterator();
			while (itr2.hasNext())
			{
				FlowLineNode<CSFlowLineData> fln2 = itr2.next();
				
			}
		}
	}
	
}