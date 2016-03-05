package cn.yyx.contentassist.codeutils;

public class finalFieldRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public finalFieldRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}

}
