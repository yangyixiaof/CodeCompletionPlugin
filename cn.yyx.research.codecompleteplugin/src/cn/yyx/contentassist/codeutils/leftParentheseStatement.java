package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class leftParentheseStatement extends statement{
	
	int count = 0;
	
	public leftParentheseStatement(int count) {
		this.count = count;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof leftParentheseStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof leftParentheseStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}