package cn.yyx.contentassist.codeutils;

public class fieldAccess extends referedExpression{
	
	identifier name = null;
	referedExpression rexp = null;
	
	public fieldAccess(identifier name, referedExpression rexp) {
		this.name = name;
		this.rexp = rexp;
	}
	
}
