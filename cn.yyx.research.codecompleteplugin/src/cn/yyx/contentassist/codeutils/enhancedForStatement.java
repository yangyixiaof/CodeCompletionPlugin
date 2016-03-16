package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class enhancedForStatement extends statement{
	
	type rt = null;
	referedExpression rexp = null;
	
	public enhancedForStatement(type rt, referedExpression rexp) {
		this.rt = rt;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enhancedForStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enhancedForStatement)
		{
			return 0.4 + 0.6*(0.6*rt.Similarity(((enhancedForStatement) t).rt) + 0.4*(rexp.Similarity(((enhancedForStatement) t).rexp)));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder tpsb = new StringBuilder("");
		rt.HandleCodeSynthesis(squeue, handler, tpsb, null);
		StringBuilder resb = new StringBuilder("");
		rexp.HandleCodeSynthesis(squeue, handler, resb, null);
		squeue.add("for (" + tpsb.toString() + " et" + " : " + resb.toString() + ") {\n}");
		return false;
	}
	
}