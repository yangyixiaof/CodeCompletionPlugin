package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class switchStatement extends statement{
	
	referedExpression rexp = null;
	
	public switchStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof switchStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof switchStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((switchStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}