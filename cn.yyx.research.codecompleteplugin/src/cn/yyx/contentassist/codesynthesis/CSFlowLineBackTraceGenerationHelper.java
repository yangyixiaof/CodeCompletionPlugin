package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class CSFlowLineBackTraceGenerationHelper {

	// TODO extreme case : start node and stop node is same and is itself not considered.
	
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
		FlowLineNode<CSFlowLineData> thelastone = mergestart;
		String preid = null;
		while (mergestart != stopnode) {
			FlowLineNode<CSFlowLineData> two = mergestart;
			FlowLineNode<CSFlowLineData> one = SearchForWholeNode(two.getPrev());
			if (one == two)
			{
				// reach to the head of the queue.
				break;
			}
			preid = ConcateTwoNodes(one, two.getPrev(), two, startnode, preid, squeue, smthandler);
			mergestart = one;
			thelastone = one;
		}
		
		if (mergestart == null)
		{
			System.out.println("Back merge stop node and start node not in a block. Serious error, the system will exit.");
			System.exit(1);
		}
		
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		// String id = GetConcateId(startnode, stopnode);
		if (preid == null)
		{
			result.add(startnode);
		}
		else
		{
			result.add(thelastone.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(preid));
		}
		return result;
	}
	
	private static String ConcateTwoNodes(FlowLineNode<CSFlowLineData> one, FlowLineNode<CSFlowLineData> onestart, FlowLineNode<CSFlowLineData> two, FlowLineNode<CSFlowLineData> twostart, String preid, CSFlowLineQueue squeue, CSStatementHandler smthandler) throws TypeConflictException
	{
		assert (twostart != null && preid == null) && (twostart == null && preid != null);
		String twoid = preid;
		if (preid == null)
		{
			twoid = GetConcateId(twostart, two);
		}
		if (two.getData().getSynthesisCodeManager().getBlockstart() != null)
		{
			two = two.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(twoid);
		}
		String oneid = GetConcateId(two.getPrev(), one);
		if (one.getData().getSynthesisCodeManager().getBlockstart() != null)
		{
			one = one.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(oneid);
		}
		
		FlowLineNode<CSFlowLineData> tres = CSFlowLineHelper.ConcateTwoFlowLineNode(null, one, null, two, null, TypeComputationKind.NotSureOptr, squeue, smthandler, null);
		String tresid = oneid + "." + twoid;
		one.getData().getSynthesisCodeManager().AddSynthesisCode(tresid, tres);
		
		twostart.getData().getSynthesisCodeManager().setBlockstart(one);
		two.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		onestart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		one.getData().getSynthesisCodeManager().setBlockstart(one);
		
		return tresid;
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
			CSStatementHandler smthandler, FlowLineNode<CSFlowLineData> startnode, FlowLineNode<CSFlowLineData> stopnode) throws CodeSynthesisException {
		
		FlowLineNode<CSFlowLineData> queuestartnode = squeue.getLast();
		FlowLineNode<CSFlowLineData> snqueuestartnode = SearchForWholeNode(queuestartnode);
		String preid = ConcateTwoNodes(snqueuestartnode, queuestartnode, startnode, startnode, GetConcateId(startnode, startnode), squeue, smthandler);
		
		FlowLineNode<CSFlowLineData> mergestart = snqueuestartnode;
		FlowLineNode<CSFlowLineData> thelastone = mergestart;
		while (mergestart != stopnode) {
			FlowLineNode<CSFlowLineData> two = mergestart;
			FlowLineNode<CSFlowLineData> one = SearchForWholeNode(two.getPrev());
			if (one == two)
			{
				// reach to the head of the queue.
				break;
			}
			preid = ConcateTwoNodes(one, two.getPrev(), two, startnode, preid, squeue, smthandler);
			mergestart = one;
			thelastone = one;
		}
		
		if (mergestart == null)
		{
			System.out.println("Back merge stop node and start node not in a block. Serious error, the system will exit.");
			System.exit(1);
		}
		
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		// String id = GetConcateId(startnode, stopnode);
		result.add(thelastone.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(preid));
		return result;
	}

	public static FlowLineNode<CSFlowLineData> GetWholeNodeCode(FlowLineNode<CSFlowLineData> last) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> start = SearchForWholeNode(last);
		return start.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(GetConcateId(last, start));
	}

	public static void SearchAndModifyLeftParentheseNode(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			int times) {
		FlowLineNode<CSFlowLineData> last = squeue.getLast();
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null && times > 0)
		{
			CSFlowLineData td = tmp.getData();
			if (td instanceof CSLeftParenInfoData)
			{
				
			}
		}
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