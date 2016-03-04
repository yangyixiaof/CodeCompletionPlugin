package cn.yyx.contentassist.codeutils;

public class instanceofExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	type type = null;
	
	public instanceofExpressionStatement(referedExpression rexp, type type) {
		this.rexp = rexp;
		this.type = type;
	}

}
