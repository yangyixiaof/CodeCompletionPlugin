package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class nameStatement extends expressionStatement{
	
	identifier name = null;
	
	public nameStatement(identifier name) {
		this.name = name;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
