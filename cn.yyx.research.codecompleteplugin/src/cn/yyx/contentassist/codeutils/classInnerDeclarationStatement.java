package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class classInnerDeclarationStatement extends statement{
	
	identifier name = null;
	
	public classInnerDeclarationStatement(identifier name) {
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classInnerDeclarationStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classInnerDeclarationStatement)
		{
			return 0.7 + 0.3*(name.Similarity(((classInnerDeclarationStatement) t).name));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}