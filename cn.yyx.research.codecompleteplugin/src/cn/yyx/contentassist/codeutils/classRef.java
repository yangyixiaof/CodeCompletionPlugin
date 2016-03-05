package cn.yyx.contentassist.codeutils;

public class classRef extends type{
	
	int scope = -1;
	int off = -1;

	public classRef(int scope2, int off2) {
		this.scope = scope2;
		this.off = off2;
	}

}
