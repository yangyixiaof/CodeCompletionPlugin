package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class synchronizedStatement extends statement{
	
	referedExpression rexp = null;
	
	public synchronizedStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof synchronizedStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof synchronizedStatement)
		{
			return 0.6 + 0.4*(rexp.Similarity(((synchronizedStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}