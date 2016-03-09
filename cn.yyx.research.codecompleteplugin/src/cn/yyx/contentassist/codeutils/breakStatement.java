package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class breakStatement extends statement{
	
	identifier id = null;
	
	public breakStatement(identifier name) {
		this.id = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof breakStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof breakStatement)
		{
			return 0.6 + 0.4*(id.Similarity(((breakStatement) t).id));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}