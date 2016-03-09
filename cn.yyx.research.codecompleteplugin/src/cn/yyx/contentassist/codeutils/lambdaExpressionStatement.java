package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class lambdaExpressionStatement extends statement{
	
	typeList tlist = null;
	
	public lambdaExpressionStatement(typeList tlist) {
		this.tlist = tlist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			return 0.4 + 0.6*(tlist.Similarity(((lambdaExpressionStatement) t).tlist));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}