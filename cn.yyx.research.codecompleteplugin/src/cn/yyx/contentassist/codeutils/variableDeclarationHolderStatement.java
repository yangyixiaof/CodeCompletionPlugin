package cn.yyx.contentassist.codeutils;

public class variableDeclarationHolderStatement extends statement{
	
	referedExpression rexp = null;
	
	public variableDeclarationHolderStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

}
