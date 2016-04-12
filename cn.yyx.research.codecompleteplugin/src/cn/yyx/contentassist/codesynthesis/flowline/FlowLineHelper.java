package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codeutils.statement;

public class FlowLineHelper {
	
	public static List<Sentence> LastNeededSentenceQueue(FlowLineNode<Sentence> fln, int needsize) {
		List<Sentence> result = new LinkedList<Sentence>();
		FlowLineNode<Sentence> tempfln = fln;
		while (tempfln != null && needsize > 0) {
			needsize--;
			result.add(0, tempfln.getData());
			tempfln = tempfln.getPrev();
		}
		return result;
	}
	
	public static List<statement> LastToFirstStatementQueue(FlowLineNode<Sentence> fln) {
		List<statement> result = new LinkedList<statement>();
		FlowLineNode<Sentence> tempfln = fln;
		while (tempfln != null) {
			result.add(0, tempfln.getData().getSmt());
			tempfln = tempfln.getPrev();
		}
		return result;
	}
	
	public static List<statement> LastToFirstStatementQueueWithAddedStatement(FlowLineNode<Sentence> fln, statement smt) {
		List<statement> result = LastToFirstStatementQueue(fln);
		result.add(smt);
		return result;
	}
	
	// very simple cache. LastNeededSentenceQueue method used only.
	private static List<Sentence> setelistref = null;
	private static Sentence seteref = null;
	
	public static List<Sentence> LastNeededSentenceQueue(FlowLineNode<CSFlowLineData> tail, CodeSynthesisFlowLines csfl,
			int needsize) {
		CSFlowLineData data = tail.getData();
		if (seteref != null && seteref == data.getSete())
		{
			assert setelistref != null;
			return setelistref;
		}
		List<Sentence> result = new LinkedList<Sentence>();
		FlowLineNode<CSFlowLineData> tmp = tail;
		while (needsize > 0 && tmp != null)
		{
			result.add(0, tmp.getData().getSete());
			tmp = tmp.getPrev();
			needsize--;
		}
		if (needsize > 0)
		{
			String id = tmp.getData().getId();
			FlowLineNode<Sentence> cnct = csfl.GetConnect(id);
			List<Sentence> tmpres = LastNeededSentenceQueue(cnct, needsize);
			result.addAll(0, tmpres);
		}
		return result;
	}
	
}