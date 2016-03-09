package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class throwStatement extends statement{
	
	referedExpression rexp = null;
	
	public throwStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof throwStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof throwStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}