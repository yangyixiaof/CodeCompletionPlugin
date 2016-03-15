package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class anonymousClassBeginStatement extends statement{
	
	public anonymousClassBeginStatement() {
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof anonymousClassBeginStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof anonymousClassBeginStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		return false;
	}
	
}