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
	
}