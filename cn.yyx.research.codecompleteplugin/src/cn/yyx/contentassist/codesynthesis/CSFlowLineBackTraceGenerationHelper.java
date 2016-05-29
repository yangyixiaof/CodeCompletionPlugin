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
		
		FlowLineNode<CSFlowLineData> mergestart = SearchForWholeNode(startnode);
		FlowLineNode<CSFlowLineData> thelastone = mergestart;
		String preid = null;
		if (startnode != mergestart)
		{
			preid = startnode.getData().getSynthesisCodeManager().getBlocktostartid();
		}
		while (mergestart != stopnode) {
			FlowLineNode<CSFlowLineData> two = mergestart;
			FlowLineNode<CSFlowLineData> one = SearchForWholeNode(two.getPrev());
			if (one == null)
			{
				break;
			}
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
			// the content from startnode to mergestart.
			result.add(startnode);
		}
		else
		{
			FlowLineNode<CSFlowLineData> lp = thelastone.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(preid);
			if (lp == null)
			{
				System.err.println("What the fuck! synthesis code is null?");
				System.exit(1);
			}
			result.add(lp);
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
		FlowLineNode<CSFlowLineData> twosyn = null;
		if (twostart.getData().getSynthesisCodeManager().getBlockstart() != null) {
			twosyn = two.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(twoid);
		} else {
			twosyn = two;
		}
		String oneid = GetConcateId(onestart, one); // two.getPrev()
		FlowLineNode<CSFlowLineData> onesyn = null;
		if (onestart.getData().getSynthesisCodeManager().getBlockstart() != null) {
			onesyn = one.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(oneid);
		} else {
			onesyn = one;
		}
		
		// debugging code, not remove.
		if (two == null)
		{
			new Exception("two is null.").printStackTrace();
		}
		
		FlowLineNode<CSFlowLineData> tres = CSFlowLineHelper.ConcateTwoFlowLineNode(null, onesyn, null, twosyn, null, squeue, smthandler, null);
		String tresid = oneid + "." + twoid;
		
		// two.getData().getSynthesisCodeManager().setBlockstart(null, null);
		// onestart.getData().getSynthesisCodeManager().setBlockstart(null, null);
		// one.getData().getSynthesisCodeManager().setBlockstart(null, null);
		
		one.getData().getSynthesisCodeManager().AddSynthesisCode(tresid, tres);
		twostart.getData().getSynthesisCodeManager().setBlockstart(one, tresid);
		tres.getData().setCsep(twostart.getData().getCsep());
		tres.getData().setTck(twostart.getData().getTck());
		
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
			tmp = tmp.getPrev();
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

	public static FlowLineNode<CSFlowLineData> SearchForWholeNode(FlowLineNode<CSFlowLineData> tailnode) throws CodeSynthesisException {
		if (tailnode == null)
		{
			return null;
		}
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
		
		FlowLineNode<CSFlowLineData> bs = startnode.getData().getSynthesisCodeManager().getBlockstart();
		FlowLineNode<CSFlowLineData> mergestart = null;
		String preid = null;
		if (bs != null) {
			mergestart = bs;
			preid = startnode.getData().getSynthesisCodeManager().getBlocktostartid();
		} else {
			FlowLineNode<CSFlowLineData> queuestartnode = squeue.getLast();
			FlowLineNode<CSFlowLineData> snqueuestartnode = SearchForWholeNode(queuestartnode);
			preid = ConcateTwoNodes(snqueuestartnode, queuestartnode, startnode, startnode, GetConcateId(startnode, startnode), squeue, smthandler);
			mergestart = snqueuestartnode;
		}
		FlowLineNode<CSFlowLineData> thelastone = mergestart;
		while (mergestart != stopnode) {
			FlowLineNode<CSFlowLineData> two = mergestart;
			FlowLineNode<CSFlowLineData> one = SearchForWholeNode(two.getPrev());
			if (one == null)
			{
				break;
			}
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

	private static void PreAddExtraLastNodeToStopNode(FlowLineNode<CSFlowLineData> snode) {
		snode.getData().getExtraData().AddExtraData(CSDataMetaInfo.LastNode, snode);
	}

	/*public static FlowLineNode<CSFlowLineData> GetWholeNodeCode(FlowLineNode<CSFlowLineData> last) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> start = SearchForWholeNode(last);
		if (start == last)
		{
			return last;
		}
		return start.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(GetConcateId(last, start));
	}*/
	
}