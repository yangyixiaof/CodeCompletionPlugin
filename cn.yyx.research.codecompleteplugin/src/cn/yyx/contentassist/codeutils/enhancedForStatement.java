package cn.yyx.contentassist.codeutils;

public class enhancedForStatement extends statement{
	
	type rt = null;
	referedExpression rexp = null;
	
	public enhancedForStatement(type rt, referedExpression rexp) {
		this.rt = rt;
		this.rexp = rexp;
	}

}
