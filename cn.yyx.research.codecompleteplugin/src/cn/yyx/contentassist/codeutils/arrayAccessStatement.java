package cn.yyx.contentassist.codeutils;

public class arrayAccessStatement extends statement{
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	
	public arrayAccessStatement(referedExpression rarr, referedExpression rexp) {
		this.rarr = rarr;
		this.rexp = rexp;
	}
	
}
