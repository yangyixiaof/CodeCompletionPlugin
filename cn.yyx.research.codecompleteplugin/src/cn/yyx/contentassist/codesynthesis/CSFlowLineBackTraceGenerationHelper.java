package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;

public class CSFlowLineBackTraceGenerationHelper {

	/**
	 * Reminder : Before invoking this method, the related '@Em' or '@(' counts
	 * should be computed.
	 * 
	 * @param squeue
	 * @param startnode
	 * @param stopnode
	 */
	public static void GenerateSynthesisCode(CSFlowLineQueue squeue, FlowLineNode<CSFlowLineData> startnode,
			FlowLineNode<CSFlowLineData> stopnode) {
		// before invoking this method, the related '@Em' or '@(' counts should
		// be computed.
		// start node must be the descendant of the stop node.
		// the generated code includes start node and stop node.
		// the start node itself must be handled before invoke this function.

		FlowLineNode<CSFlowLineData> mergestart = startnode;
		while (mergestart != stopnode) {
			FlowLineNode<CSFlowLineData> sn = SearchForWholeNode(mergestart);
			FlowLineNode<CSFlowLineData> pvsn = sn.getPrev();
			FlowLineNode<CSFlowLineData> ssn = SearchForWholeNode(pvsn);
			
			mergestart = ssn;
		}

	}

	private static FlowLineNode<CSFlowLineData> SearchForWholeNode(FlowLineNode<CSFlowLineData> tailnode) {
		String expectkey = null;
		FlowLineNode<CSFlowLineData> tmp = tailnode;
		FlowLineNode<CSFlowLineData> tmppre = null;
		while (tmp != null) {
			CSFlowLineData tmpdata = tmp.getData();

			if (expectkey == null) {
				expectkey = tmpdata.getId() + "";
			} else {
				expectkey = tmpdata.getId() + "." + expectkey;
			}

			FlowLineNode<CSFlowLineData> pv = tmp.getPrev();
			if (pv == null) {
				return tmp;
			} else {
				CSFlowLineData pvdata = pv.getData();
				SynthesisCodeManager pvscm = pvdata.getSynthesisCodeManager();
				FlowLineNode<CSFlowLineData> sc = pvscm.GetSynthesisCodeByKey(expectkey);
				if (sc == null) {
					return tmp;
				}
			}
			tmppre = tmp;
			tmp = tmp.getPrev();
		}
		return tmppre;
	}

}