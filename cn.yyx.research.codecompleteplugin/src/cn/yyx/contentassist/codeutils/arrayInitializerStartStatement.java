package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class arrayInitializerStartStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayInitializerStartStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
