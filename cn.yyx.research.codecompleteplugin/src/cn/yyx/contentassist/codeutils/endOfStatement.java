package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class endOfStatement extends statement{
	
	boolean fullEnd = false;
	
	public endOfStatement(boolean fullEnd) {
		this.fullEnd = fullEnd;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof endOfStatement)
		{
			if (fullEnd == ((endOfStatement)t).fullEnd)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof endOfStatement)
		{
			if (fullEnd == ((endOfStatement)t).fullEnd)
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		if (fullEnd)
		{
			cstack.pop();
		}
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.SymbolMark);
		cs.AddOneData(";", null);
		squeue.add(cs);
		boolean conflict = squeue.MergeBackwardAsFarAsItCan();
		return conflict;
	}
	
}