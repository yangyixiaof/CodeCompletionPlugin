package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class condExpColonMarkStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof condExpColonMarkStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof condExpColonMarkStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
