package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class literalStatement extends expressionStatement{
	
	literal lt = null;
	
	public literalStatement(literal lt) {
		this.lt = lt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.CouldThoughtSame(((literalStatement) t).lt);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.Similarity(((literalStatement) t).lt);
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}