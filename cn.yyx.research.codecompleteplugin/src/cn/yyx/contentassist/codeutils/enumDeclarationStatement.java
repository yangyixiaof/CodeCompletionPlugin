package cn.yyx.contentassist.codeutils;

public class enumDeclarationStatement extends statement{
	
	identifier name = null;
	
	public enumDeclarationStatement(identifier name) {
		this.name = name;
	}

}
