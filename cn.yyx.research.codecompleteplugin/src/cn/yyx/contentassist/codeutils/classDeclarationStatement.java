package cn.yyx.contentassist.codeutils;

public class classDeclarationStatement extends statement{
	
	identifier name = null;
	
	public classDeclarationStatement(identifier name) {
		this.name = name;
	}

}
