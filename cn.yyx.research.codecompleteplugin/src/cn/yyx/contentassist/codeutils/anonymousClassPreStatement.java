package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class anonymousClassPreStatement extends statement{
	
	identifier id = null;
	
	public anonymousClassPreStatement(identifier id) {
		this.id = id;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			if (id.CouldThoughtSame(((anonymousClassPreStatement)t).id))
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
