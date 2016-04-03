package cn.yyx.contentassist.flowline;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.Sentence;
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
	
	
	
}