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
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO Auto-generated method stub
		if (t instanceof annotationTypeMemberDeclarationStatement)
		{
			if (type.CouldThoughtSame(((annotationTypeMemberDeclarationStatement)t).type))
			{
				return true;
			}
		}
		return false;
	}
	
}