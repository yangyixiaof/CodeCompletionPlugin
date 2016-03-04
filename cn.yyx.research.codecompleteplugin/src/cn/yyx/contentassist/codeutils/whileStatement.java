package cn.yyx.contentassist.codeutils;

public class whileStatement extends statement{
	
	referedExpression rexp = null;
	
	public whileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
