package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class anonymousClassBeginStatement extends statement{
	
	identifier id = null;
	
	public anonymousClassBeginStatement(identifier id) {
		this.id = id;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof anonymousClassBeginStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof anonymousClassBeginStatement)
		{
			return 1;
		}
		return 0;
	}
	
}