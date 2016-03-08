package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class annotationTypeMemberDeclarationStatement extends statement{
	
	type type = null;
	referedExpression drexp = null;
	
	public annotationTypeMemberDeclarationStatement(type type, referedExpression drexp) {
		this.type = type;
		this.drexp = drexp;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		// TODO
	}
	
	@Override
	public boolean CouldThoughtSame(statement t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double Similarity(statement t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}