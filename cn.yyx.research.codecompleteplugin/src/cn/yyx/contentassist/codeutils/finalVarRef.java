package cn.yyx.contentassist.codeutils;

public class finalVarRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public finalVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}

}
