package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class nameStatement extends expressionStatement{
	
	identifier name = null;
	
	public nameStatement(identifier name) {
		this.name = name;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof nameStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof nameStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
