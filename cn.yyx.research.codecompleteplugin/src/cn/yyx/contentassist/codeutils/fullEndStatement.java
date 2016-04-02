package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.CSBackQueue;
import cn.yyx.contentassist.codesynthesis.CSParLineNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

public class fullEndStatement extends statement {

	@Override
	public boolean HandleCodeSynthesis(CSBackQueue squeue, List<CSParLineNode> nextpars) {
		CSNode cs = new CSNode(CSNodeType.SymbolMark);
		cs.AddOneData(";", null);
		squeue.add(cs);
		boolean conflict = squeue.MergeBackwardAsFarAsItCan();
		return false;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fullEndStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fullEndStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer res = cstack.peek();
		if (res != StructureSignalMetaInfo.AllKindWaitingOver)
		{
			return true;
		}
		cstack.pop();
		return false;
	}

}
