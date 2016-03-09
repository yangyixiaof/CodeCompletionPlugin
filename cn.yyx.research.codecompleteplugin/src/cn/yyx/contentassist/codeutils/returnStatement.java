package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class returnStatement extends statement{
	
	referedExpression rexp = null;
	
	public returnStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof returnStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof returnStatement)
		{
			double prob = 1;
			if ((rexp == null && ((returnStatement)t).rexp != null) || (rexp != null && ((returnStatement)t).rexp == null))
			{
				prob = 0.7;
			}
			if (rexp != null && ((returnStatement)t).rexp != null)
			{
				prob = rexp.Similarity(((returnStatement) t).rexp);
			}
			return 0.4 + 0.6*(prob);
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}