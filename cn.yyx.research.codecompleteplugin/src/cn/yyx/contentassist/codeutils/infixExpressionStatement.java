package cn.yyx.contentassist.codeutils;

public class infixExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	String optr = null;
	referedExpression lexp = null;
	
	public infixExpressionStatement(referedExpression lexp, String optr, referedExpression rexp) {
		this.lexp = lexp;
		this.rexp = rexp;
		this.optr = optr;
	}

}
