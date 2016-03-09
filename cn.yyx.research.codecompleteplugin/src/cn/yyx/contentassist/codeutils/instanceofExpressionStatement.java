package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class instanceofExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	type type = null;
	
	public instanceofExpressionStatement(referedExpression rexp, type type) {
		this.rexp = rexp;
		this.type = type;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return 0.4 + 0.6*(0.5*rexp.Similarity(((instanceofExpressionStatement) t).rexp) + 0.5*type.Similarity(((instanceofExpressionStatement) t).type));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}