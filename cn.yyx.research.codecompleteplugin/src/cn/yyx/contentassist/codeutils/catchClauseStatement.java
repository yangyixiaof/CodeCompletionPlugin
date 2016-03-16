package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class catchClauseStatement extends statement{
	
	type rt = null;
	
	public catchClauseStatement(type rt) {
		this.rt = rt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return 0.5 + 0.5*(rt.Similarity(((catchClauseStatement)t).rt));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder tpsb = new StringBuilder("");
		rt.HandleCodeSynthesis(squeue, handler, tpsb, null);
		squeue.add("catch (" + tpsb.toString() + " e) {\n}");
		return false;
	}
	
}