package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class enumConstantDeclarationStatement extends statement{
	
	identifier name = null;
	argumentList arglist = null;
	
	public enumConstantDeclarationStatement(identifier name, argumentList arglist) {
		this.name = name;
		this.arglist = arglist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			if (name.CouldThoughtSame(((enumConstantDeclarationStatement) t).name))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			return 0.4 + 0.6*(name.Similarity(((enumConstantDeclarationStatement) t).name));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}