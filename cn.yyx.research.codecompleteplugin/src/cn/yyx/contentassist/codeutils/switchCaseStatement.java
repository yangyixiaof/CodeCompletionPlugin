package cn.yyx.contentassist.codeutils;

public class switchCaseStatement extends statement{
	
	referedExpression rexp = null;
	
	public switchCaseStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
