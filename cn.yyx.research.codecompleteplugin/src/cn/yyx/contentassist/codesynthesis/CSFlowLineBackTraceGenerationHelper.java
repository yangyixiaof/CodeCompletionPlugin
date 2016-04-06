package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineBackTraceGenerationHelper {
	
	/**
	 * Reminder : Before invoking this method, the related '@Em' or '@(' counts should be computed.
	 * @param squeue
	 * @param stopnode
	 */
	public static void GenerateSynthesisCode(CSFlowLineQueue squeue, FlowLineNode<CSFlowLineData> start, FlowLineNode<CSFlowLineData> stopnode)
	{
		// before invoking this method, the related '@Em' or '@(' counts should be computed.
		
	}
	
}