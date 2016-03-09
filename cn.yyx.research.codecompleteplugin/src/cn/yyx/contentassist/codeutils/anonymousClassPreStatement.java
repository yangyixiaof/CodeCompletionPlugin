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

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((anonymousClassPreStatement) t).id));
		}
		return 0;
	}
	
}
