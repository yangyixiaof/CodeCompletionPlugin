package cn.yyx.contentassist.codeutils;

public class methodDeclarationStatement extends statement{
	
	identifier name = null;
	argumentList args = null;
	
	public methodDeclarationStatement(identifier name, argumentList argList) {
		this.name = name;
		this.args = argList;
	}

}
