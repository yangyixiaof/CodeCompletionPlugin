package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class labeledStatement extends statement{
	
	identifier name = null;
	
	public labeledStatement(identifier name) {
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof labeledStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof labeledStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}