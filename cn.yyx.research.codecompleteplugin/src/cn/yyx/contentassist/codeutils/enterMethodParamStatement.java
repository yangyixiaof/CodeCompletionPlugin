package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class enterMethodParamStatement extends statement{
	
	int times = -1;
	
	public enterMethodParamStatement(int times) {
		this.times = times;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enterMethodParamStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enterMethodParamStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		squeue.add("@Em", times);
		return false;
	}
	
}