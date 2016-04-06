package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineBackTraceGenerationHelper {
	
	/**
	 * Reminder : Before invoking this method, the related '@Em' or '@(' counts should be computed.
	 * @param squeue
	 * @param startnode
	 * @param stopnode
	 */
	public static void GenerateSynthesisCode(CSFlowLineQueue squeue, FlowLineNode<CSFlowLineData> startnode, FlowLineNode<CSFlowLineData> stopnode)
	{
		// before invoking this method, the related '@Em' or '@(' counts should be computed.
		// start node must be the descendant of the stop node.
		// the generated code includes start node and stop node.
		// the start node itself must be handled before invoke this function.
		
		FlowLineNode<CSFlowLineData> last = squeue.getLast();
		FlowLineNode<CSFlowLineData> tmp = last;
		boolean startrun = false;
		while (tmp != null)
		{
			if (tmp == startnode)
			{
				startrun = true;
			}
			if (startrun)
			{
				
			}
			if (tmp == stopnode)
			{
				break;
			}
		}
		
	}
	
}