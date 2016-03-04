package cn.yyx.contentassist.codeutils;

public class nameStatement extends expressionStatement{
	
	identifier name = null;
	
	public nameStatement(identifier name) {
		this.name = name;
	}

}
