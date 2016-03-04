package cn.yyx.contentassist.codeutils;

public class synchronizedStatement extends statement{
	
	referedExpression rexp = null;
	
	public synchronizedStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
