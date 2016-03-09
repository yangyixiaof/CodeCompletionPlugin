package cn.yyx.contentassist.codeutils;

import java.util.Stack;

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
	
}