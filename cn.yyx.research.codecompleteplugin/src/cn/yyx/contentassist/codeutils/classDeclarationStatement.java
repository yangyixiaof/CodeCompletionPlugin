package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class classDeclarationStatement extends statement{
	
	identifier name = null;
	
	public classDeclarationStatement(identifier name) {
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classDeclarationStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classDeclarationStatement)
		{
			return 0.7 + 0.3*(name.Similarity(((classDeclarationStatement) t).name));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}