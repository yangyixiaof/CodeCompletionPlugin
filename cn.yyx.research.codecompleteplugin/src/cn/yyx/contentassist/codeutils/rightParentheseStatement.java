package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class rightParentheseStatement extends statement{
	
	int count = 0;
	
	public rightParentheseStatement(int count) {
		this.count = count;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}