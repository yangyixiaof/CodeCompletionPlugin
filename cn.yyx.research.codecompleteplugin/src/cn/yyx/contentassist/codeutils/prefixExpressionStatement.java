package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class prefixExpressionStatement extends expressionStatement{
	
	String optr = null;
	referedExpression rexp = null;
	
	public prefixExpressionStatement(String optr, referedExpression rexp) {
		this.optr = optr;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof prefixExpressionStatement)
		{
			if (optr.equals(((prefixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof prefixExpressionStatement)
		{
			if (optr.equals(((prefixExpressionStatement) t).optr))
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}