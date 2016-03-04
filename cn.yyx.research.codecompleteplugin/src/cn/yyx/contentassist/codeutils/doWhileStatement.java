package cn.yyx.contentassist.codeutils;

public class doWhileStatement extends statement{
	
	referedExpression rexp = null;
	
	public doWhileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
