package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSDataMetaInfo;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class CSFlowLineBackTraceGenerationHelper {

	// TODO long term: debug to do, remember to do type check in some place.
	// TODO long term: partialMethodPreRerferedExpressionEndStatement is not considered. Not know this question.
	
	// Solved. extreme case : start node and stop node is same and is itself not considered.
	// Solved. remember to add extra data Last Node to extra data of the last node.
	
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
		// before invoking this method, the related '@Em' or '@(' counts should be computed.
		// start node must be the descendant of the stop node.
		// the generated code includes start node and stop node.
		// the start node itself must be handled before invoke this function.
		PreAddExtraLastNodeToStopNode(startnode);
		if (stopnode == null)
		{
			stopnode = squeue.BackSearchForHead();
			if (stopnode == null)
			{
				stopnode = startnode;
			}
		}
		
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
	
	private static String ConcateTwoNodes(FlowLineNode<CSFlowLineData> one, FlowLineNode<CSFlowLineData> onestart, FlowLineNode<CSFlowLineData> two, FlowLineNode<CSFlowLineData> twostart, String preid, CSFlowLineQueue squeue, CSStatementHandler smthandler) throws CodeSynthesisException
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
		String oneid = GetConcateId(onestart, one); // two.getPrev()
		if (one.getData().getSynthesisCodeManager().getBlockstart() != null)
		{
			one = one.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(oneid);
		}
		
		FlowLineNode<CSFlowLineData> tres = CSFlowLineHelper.ConcateTwoFlowLineNode(null, one, null, two, null, squeue, smthandler, null, null);
		String tresid = oneid + "." + twoid;
		one.getData().getSynthesisCodeManager().AddSynthesisCode(tresid, tres);
		
		two.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		twostart.getData().getSynthesisCodeManager().setBlockstart(one);
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
		PreAddExtraLastNodeToStopNode(startnode);
		if (stopnode == null)
		{
			stopnode = squeue.BackSearchForHead();
			if (stopnode == null)
			{
				stopnode = startnode;
			}
		}
		
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

	private static void PreAddExtraLastNodeToStopNode(FlowLineNode<CSFlowLineData> stopnode) {
		stopnode.getData().getExtraData().AddExtraData(CSDataMetaInfo.LastNode, stopnode);
	}

	public static FlowLineNode<CSFlowLineData> GetWholeNodeCode(FlowLineNode<CSFlowLineData> last) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> start = SearchForWholeNode(last);
		return start.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(GetConcateId(last, start));
	}
	
}