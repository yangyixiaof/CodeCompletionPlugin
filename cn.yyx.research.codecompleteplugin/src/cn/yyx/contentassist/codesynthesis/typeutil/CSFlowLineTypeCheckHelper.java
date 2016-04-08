package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineTypeCheckHelper {
	
	public static List<FlowLineNode<CSFlowLineData>> RetainTheFallThroughFlowLineNodes(List<FlowLineNode<CSFlowLineData>> ls, Class<?> checkclass)
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = ls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			Class<?> c = fln.getData().getDcls();
			if (TypeCheckHelper.CanBeMutualCast(checkclass, c))
			{
				result.add(fln);
			}
		}
		return result;
	}
	
}