package cn.yyx.contentassist.codeutils;

public class castExpressionStatement extends expressionStatement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public castExpressionStatement(type tp, referedExpression rexp) {
		this.tp = tp;
		this.rexp = rexp;
	}
	
}
