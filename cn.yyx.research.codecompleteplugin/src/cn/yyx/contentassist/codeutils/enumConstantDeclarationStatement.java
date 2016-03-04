package cn.yyx.contentassist.codeutils;

public class enumConstantDeclarationStatement extends statement{
	
	identifier name = null;
	argumentList arglist = null;
	
	public enumConstantDeclarationStatement(identifier name, argumentList arglist) {
		this.name = name;
		this.arglist = arglist;
	}

}
