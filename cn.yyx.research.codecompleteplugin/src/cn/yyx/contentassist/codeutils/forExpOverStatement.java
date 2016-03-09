package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class forExpOverStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forExpOverStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forExpOverStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
