package cn.yyx.contentassist.codeutils;

public class classInnerDeclarationStatement extends statement{
	
	identifier name = null;
	
	public classInnerDeclarationStatement(identifier name) {
		this.name = name;
	}

}
