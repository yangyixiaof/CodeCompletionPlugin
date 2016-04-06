package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.commonutils.CheckUtil;

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
		FlowLineNode<CSFlowLineData> tmppre = null;
		String expectkey = null;
		boolean startrun = false;
		while (tmp != null)
		{
			if (startrun)
			{
				CheckUtil.CheckStartNodeMustNotHaveAnyHoles(startnode);
				CSFlowLineData tmpdata = tmp.getData();
				SynthesisCodeManager tmpscm = tmpdata.getSynthesisCodeManager();
				expectkey = tmpdata.getId() + "." + expectkey;
				SynthesisCode sc = tmpscm.GetSynthesisCodeByKey(expectkey);
				
			}
			if (tmp == startnode)
			{
				startrun = true;
				expectkey = tmp.getData().getId() + "";
			}
			tmppre = tmp;
			if (tmp == stopnode)
			{
				break;
			}
			tmp = tmp.getPrev();
		}
		
	}
	
}