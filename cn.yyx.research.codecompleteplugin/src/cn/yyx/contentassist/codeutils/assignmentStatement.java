package cn.yyx.contentassist.codeutils;

public class assignmentStatement extends expressionStatement{
	referedExpression left = null;
	String optr = null;
	referedExpression right = null;
	
	public assignmentStatement(referedExpression left, String optr, referedExpression right) {
		this.left = left;
		this.optr = optr;
		this.right = right;
	}
	
}
