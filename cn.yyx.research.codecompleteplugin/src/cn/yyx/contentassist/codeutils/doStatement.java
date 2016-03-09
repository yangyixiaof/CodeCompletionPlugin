package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class doStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof doStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof doStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}