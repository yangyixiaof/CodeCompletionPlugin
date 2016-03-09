package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class continueStatement extends statement{
	
	identifier name = null;
	
	public continueStatement(identifier name) {
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof continueStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof continueStatement)
		{
			double prob = 1;
			if (name != null)
			{
				prob = name.Similarity(((continueStatement) t).name);
			}
			return 0.6 + 0.4*(prob);
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}