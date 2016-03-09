package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class catchClauseStatement extends statement{
	
	type rt = null;
	
	public catchClauseStatement(type rt) {
		this.rt = rt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return 0.5 + 0.5*(rt.Similarity(((catchClauseStatement)t).rt));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}