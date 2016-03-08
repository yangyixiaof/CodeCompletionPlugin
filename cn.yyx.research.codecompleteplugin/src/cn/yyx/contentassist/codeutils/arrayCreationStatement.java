package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class arrayCreationStatement extends statement{
	
	type rt = null;
	
	public arrayCreationStatement(type rt) {
		this.rt = rt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			if (rt.CouldThoughtSame(((arrayCreationStatement) t).rt))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
