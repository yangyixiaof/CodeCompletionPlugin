package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class CSFlowLineBackTraceGenerationHelper {

	/**
	 * Reminder : Before invoking this method, the related '@Em' or '@(' counts
	 * should be computed.
	 * 
	 * @param squeue
	 * @param startnode
	 * @param stopnode
	 * @throws CodeSynthesisException 
	 */
	public static List<FlowLineNode<CSFlowLineData>> GenerateSynthesisCode(CSFlowLineQueue squeue, CSStatementHandler smthandler, FlowLineNode<CSFlowLineData> startnode,
			FlowLineNode<CSFlowLineData> stopnode) throws CodeSynthesisException {
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
			
			FlowLineNode<CSFlowLineData> one = ssn;
			FlowLineNode<CSFlowLineData> two = sn;
			SynthesisCodeManager ssnscm = ssn.getData().getSynthesisCodeManager();
			SynthesisCodeManager snscm = sn.getData().getSynthesisCodeManager();
			if (ssnscm.getBlockstart() != null)
			{
				one = ssnscm.GetSynthesisCodeByKey(GetConcateId(pvsn, ssn));
			}
			if (snscm.getBlockstart() != null)
			{
				two = snscm.GetSynthesisCodeByKey(GetConcateId(mergestart, sn));
			}
			
			FlowLineNode<CSFlowLineData> tres = CSFlowLineHelper.ConcateTwoFlowLineNode(null, one, null, two, null, TypeComputationKind.NotSureOptr, squeue, smthandler, null);
			String tresid = GetConcateId(startnode, ssn);
			ssnscm.AddSynthesisCode(tresid, tres);
			
			snscm.setBlockstart(ssn);
			pvsn.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
			ssnscm.setBlockstart(ssn);
			
			mergestart = ssn;
		}
		
		if (mergestart == null)
		{
			System.out.println("Back merge stop node and start node not in a block. Serious error, the system will exit.");
			System.exit(1);
		}
		
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		String id = GetConcateId(startnode, stopnode);
		result.add(stopnode.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(id));
		return result;
	}
	
	public static String GetConcateId(FlowLineNode<CSFlowLineData> startnode, FlowLineNode<CSFlowLineData> stopnode)
	{
		String fin = null;
		FlowLineNode<CSFlowLineData> tmp = startnode;
		while (tmp != stopnode)
		{
			if (fin == null)
			{
				fin = tmp.getData().getId();
			}
			else
			{
				fin = tmp.getData().getId() + "." + fin;
			}
		}
		if (fin == null)
		{
			fin = tmp.getData().getId();
		}
		else
		{
			fin = tmp.getData().getId() + "." + fin;
		}
		return fin;
	}

	private static FlowLineNode<CSFlowLineData> SearchForWholeNode(FlowLineNode<CSFlowLineData> tailnode) throws CodeSynthesisException {
		SynthesisCodeManager tailscm = tailnode.getData().getSynthesisCodeManager();
		FlowLineNode<CSFlowLineData> bs = tailscm.getBlockstart();
		if (bs == null)
		{
			return tailnode;
		}
		if (bs == SynthesisCodeManager.InternNode)
		{
			throw new CodeSynthesisException("Error happens in Search for whole node. The tail node is an intern node.");
		}
		return bs;
	}

	public static List<FlowLineNode<CSFlowLineData>> GenerateNotYetAddedSynthesisCode(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, FlowLineNode<CSFlowLineData> cnode, FlowLineNode<CSFlowLineData> cnode2) {
		
		return null;
	}

	/*
	 * private static FlowLineNode<CSFlowLineData>
	 * SearchForWholeNode(FlowLineNode<CSFlowLineData> tailnode) { String
	 * expectkey = null; FlowLineNode<CSFlowLineData> tmp = tailnode;
	 * FlowLineNode<CSFlowLineData> tmppre = null; while (tmp != null) {
	 * CSFlowLineData tmpdata = tmp.getData();
	 * 
	 * if (expectkey == null) { expectkey = tmpdata.getId() + ""; } else {
	 * expectkey = tmpdata.getId() + "." + expectkey; }
	 * 
	 * FlowLineNode<CSFlowLineData> pv = tmp.getPrev(); if (pv == null) { return
	 * tmp; } else { CSFlowLineData pvdata = pv.getData(); SynthesisCodeManager
	 * pvscm = pvdata.getSynthesisCodeManager(); FlowLineNode<CSFlowLineData> sc
	 * = pvscm.GetSynthesisCodeByKey(expectkey); if (sc == null) { return tmp; }
	 * } tmppre = tmp; tmp = tmp.getPrev(); } return tmppre; }
	 */

}