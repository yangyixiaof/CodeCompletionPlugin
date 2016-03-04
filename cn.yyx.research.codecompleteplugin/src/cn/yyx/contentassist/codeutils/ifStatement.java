package cn.yyx.contentassist.codeutils;

public class ifStatement extends statement{
	
	referedExpression rexp = null;
	
	public ifStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
