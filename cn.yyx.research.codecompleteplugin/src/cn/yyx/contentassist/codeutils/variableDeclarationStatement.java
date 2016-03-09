package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class variableDeclarationStatement extends statement{
	
	type tp = null;
	
	public variableDeclarationStatement(type tp) {
		this.tp = tp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			if (tp.CouldThoughtSame(((variableDeclarationStatement) t).tp))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			return 0.5 + 0.5*(tp.Similarity(((variableDeclarationStatement) t).tp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}