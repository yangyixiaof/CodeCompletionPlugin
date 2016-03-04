package cn.yyx.contentassist.codeutils;

public class methodReferenceStatement extends expressionStatement{
	
	identifier name = null;
	referedExpression rexp = null;
	
	public methodReferenceStatement(identifier name, referedExpression rexp) {
		this.name = name;
		this.rexp = rexp;
	}
	
}