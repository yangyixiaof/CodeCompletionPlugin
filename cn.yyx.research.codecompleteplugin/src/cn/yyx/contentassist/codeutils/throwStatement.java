package cn.yyx.contentassist.codeutils;

public class throwStatement extends statement{
	
	referedExpression rexp = null;
	
	public throwStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
