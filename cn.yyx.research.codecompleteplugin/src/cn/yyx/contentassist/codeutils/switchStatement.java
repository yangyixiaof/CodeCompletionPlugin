package cn.yyx.contentassist.codeutils;

public class switchStatement extends statement{
	
	referedExpression rexp = null;
	
	public switchStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
}