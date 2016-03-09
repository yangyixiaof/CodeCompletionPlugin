package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class whileStatement extends statement{
	
	referedExpression rexp = null;
	
	public whileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof whileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof whileStatement)
		{
			return 0.5 + 0.5*(rexp.Similarity(((whileStatement) t).rexp));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}