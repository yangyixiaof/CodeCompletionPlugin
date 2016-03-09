package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class endOfStatement extends statement{
	
	boolean fullEnd = false;
	
	public endOfStatement(boolean fullEnd) {
		this.fullEnd = fullEnd;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof endOfStatement)
		{
			if (fullEnd == ((endOfStatement)t).fullEnd)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof endOfStatement)
		{
			if (fullEnd == ((endOfStatement)t).fullEnd)
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		if (fullEnd)
		{
			cstack.pop();
		}
	}
	
}