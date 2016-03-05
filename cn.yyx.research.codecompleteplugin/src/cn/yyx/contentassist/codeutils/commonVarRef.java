package cn.yyx.contentassist.codeutils;

public class commonVarRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public commonVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}

}
