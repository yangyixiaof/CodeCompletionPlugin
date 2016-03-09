package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class infixExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	String optr = null;
	referedExpression lexp = null;
	
	public infixExpressionStatement(referedExpression lexp, String optr, referedExpression rexp) {
		this.lexp = lexp;
		this.rexp = rexp;
		this.optr = optr;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return 0.5 + 0.5*(0.5*(rexp.Similarity(((infixExpressionStatement) t).rexp)) + 0.5*(lexp.Similarity(((infixExpressionStatement) t).lexp)));
			}
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}