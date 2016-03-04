package cn.yyx.contentassist.codeutils;

public class breakStatement extends statement{
	
	identifier name = null;
	
	public breakStatement(identifier name) {
		this.name = name;
	}

}
