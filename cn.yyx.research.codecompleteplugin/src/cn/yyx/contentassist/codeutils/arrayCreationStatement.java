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

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			return 0.4 + 0.6*(rt.Similarity(((arrayCreationStatement) t).rt));
		}
		return 0;
	}

}
