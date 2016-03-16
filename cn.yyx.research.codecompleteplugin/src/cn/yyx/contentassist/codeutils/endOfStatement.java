package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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
	public void HandleOverSignal(Stack<Integer> cstack) {
		if (fullEnd)
		{
			cstack.pop();
		}
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		
		return false;
	}
	
}