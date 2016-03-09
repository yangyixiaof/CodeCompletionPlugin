package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class enumDeclarationStatement extends statement{
	
	identifier name = null;
	
	public enumDeclarationStatement(identifier name) {
		this.name = name;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enumDeclarationStatement)
		{
			if (name.CouldThoughtSame(((enumDeclarationStatement) t).name))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enumDeclarationStatement)
		{
			return 0.4 + 0.6*(name.Similarity(((enumDeclarationStatement) t).name));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

}
