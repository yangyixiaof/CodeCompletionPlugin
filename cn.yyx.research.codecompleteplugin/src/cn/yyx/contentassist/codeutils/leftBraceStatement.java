package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class leftBraceStatement extends statement{
	
	int count = 0;
	
	public leftBraceStatement(int count) {
		this.count = count;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof leftBraceStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof leftBraceStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
	}

}
