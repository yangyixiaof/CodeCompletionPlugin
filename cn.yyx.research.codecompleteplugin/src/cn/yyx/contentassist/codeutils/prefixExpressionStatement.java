package cn.yyx.contentassist.codeutils;

public class prefixExpressionStatement extends expressionStatement{
	
	String optr = null;
	referedExpression rexp = null;
	
	public prefixExpressionStatement(String optr, referedExpression rexp) {
		this.optr = optr;
		this.rexp = rexp;
	}

}
