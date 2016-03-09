package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class methodReferenceStatement extends expressionStatement{
	
	identifier name = null;
	referedExpression rexp = null;
	
	public methodReferenceStatement(identifier name, referedExpression rexp) {
		this.name = name;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			if (name.CouldThoughtSame(((methodReferenceStatement) t).name) || rexp.CouldThoughtSame(((methodReferenceStatement) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			return 0.3 + 0.7*(0.5*(name.Similarity(((methodReferenceStatement) t).name)) + 0.5*(rexp.Similarity(((methodReferenceStatement) t).rexp)));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}