package cn.yyx.contentassist.codeutils;

public class commonFieldRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public commonFieldRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}

}
