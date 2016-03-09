package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class methodDeclarationStatement extends statement{
	
	typeList typelist = null;
	identifier name = null;
	
	public methodDeclarationStatement(typeList typelist, identifier name) {
		this.typelist = typelist;
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			if (typelist.CouldThoughtSame(((methodDeclarationStatement) t).typelist) || name.CouldThoughtSame(((methodDeclarationStatement) t).name))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			return 0.3 + 0.7*(0.6*(typelist.Similarity(((methodDeclarationStatement) t).typelist)) + 0.4*(name.Similarity(((methodDeclarationStatement) t).name)));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}