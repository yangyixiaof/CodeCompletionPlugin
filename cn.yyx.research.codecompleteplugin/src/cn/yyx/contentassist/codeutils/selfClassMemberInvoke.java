package cn.yyx.contentassist.codeutils;

public class selfClassMemberInvoke extends classInvoke{
	
	referedExpression rexp = null;
	
	public selfClassMemberInvoke(referedExpression rexp) {
		this.rexp = rexp;
	}
	
}
