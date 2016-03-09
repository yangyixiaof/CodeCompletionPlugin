package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class arrayAccessStatement extends statement{
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	
	public arrayAccessStatement(referedExpression rarr, referedExpression rexp) {
		this.rarr = rarr;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayAccessStatement)
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
		if (t instanceof arrayAccessStatement)
		{
			return 0.4 + 0.6*(rarr.Similarity(((arrayAccessStatement) t).rarr) + rexp.Similarity(((arrayAccessStatement) t).rexp));
		}
		return 0;
	}
	
}