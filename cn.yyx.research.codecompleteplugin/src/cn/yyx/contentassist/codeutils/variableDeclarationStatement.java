package cn.yyx.contentassist.codeutils;

public class variableDeclarationStatement extends statement{
	
	type tp = null;
	int count = 0;
	
	public variableDeclarationStatement(type tp, int count) {
		this.tp = tp;
		this.count = count;
	}
	
}