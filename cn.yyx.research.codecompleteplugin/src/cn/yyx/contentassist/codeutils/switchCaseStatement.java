package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class switchCaseStatement extends statement{
	
	referedExpression rexp = null;
	
	public switchCaseStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof switchCaseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof switchCaseStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((switchCaseStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}
