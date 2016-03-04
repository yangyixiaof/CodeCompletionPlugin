package cn.yyx.contentassist.codeutils;

public class postfixExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	String optr = null;
	
	public postfixExpressionStatement(referedExpression rexp, String optr) {
		this.rexp = rexp;
		this.optr = optr;
	}

}
