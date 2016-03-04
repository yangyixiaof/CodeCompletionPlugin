package cn.yyx.contentassist.codeutils;

public class returnStatement extends statement{
	
	referedExpression rexp = null;
	
	public returnStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
