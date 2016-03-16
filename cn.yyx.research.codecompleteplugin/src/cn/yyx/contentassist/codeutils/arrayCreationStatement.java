package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class arrayCreationStatement extends statement{
	
	type rt = null;
	
	public arrayCreationStatement(type rt) {
		this.rt = rt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			if (rt.CouldThoughtSame(((arrayCreationStatement) t).rt))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			return 0.4 + 0.6*(rt.Similarity(((arrayCreationStatement) t).rt));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder fin = new StringBuilder("");
		StringBuilder ttp = new StringBuilder("");
		rt.HandleCodeSynthesis(squeue, handler, ttp, null);
		fin.append("new " + ttp.toString());
		if (squeue.hasHoleLast())
		{
			squeue.SetLast(squeue.getLast() + fin.toString());
		}
		return false;
	}
	
}