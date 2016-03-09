package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class postfixExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	String optr = null;
	
	public postfixExpressionStatement(referedExpression rexp, String optr) {
		this.rexp = rexp;
		this.optr = optr;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof postfixExpressionStatement)
		{
			if (optr.equals(((postfixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof postfixExpressionStatement)
		{
			if (optr.equals(((postfixExpressionStatement) t).optr))
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