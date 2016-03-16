package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class doWhileStatement extends statement{
	
	referedExpression rexp = null;
	
	public doWhileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((doWhileStatement) t).rexp));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}