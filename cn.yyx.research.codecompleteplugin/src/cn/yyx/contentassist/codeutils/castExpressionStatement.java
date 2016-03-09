package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class castExpressionStatement extends expressionStatement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public castExpressionStatement(type tp, referedExpression rexp) {
		this.tp = tp;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return 0.4 + 0.6*(tp.Similarity(((castExpressionStatement) t).tp) + rexp.Similarity(((castExpressionStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}