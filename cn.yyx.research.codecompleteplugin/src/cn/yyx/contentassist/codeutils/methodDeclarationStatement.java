package cn.yyx.contentassist.codeutils;

public class methodDeclarationStatement extends statement{
	
	typeList typelist = null;
	identifier name = null;
	
	public methodDeclarationStatement(typeList typelist, identifier name) {
		this.typelist = typelist;
		this.name = name;
	}

}
