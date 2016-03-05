package cn.yyx.contentassist.codeutils;

public class arrayType extends type{
	
	type tp = null;
	int count = 0;
	
	public arrayType(type tp, int count) {
		this.tp = tp;
		this.count = count;
	}

}
