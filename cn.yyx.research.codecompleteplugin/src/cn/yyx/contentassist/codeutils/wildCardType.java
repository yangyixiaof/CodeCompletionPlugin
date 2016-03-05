package cn.yyx.contentassist.codeutils;

public class wildCardType extends type{
	
	boolean extended = false;
	type tp = null;
	
	public wildCardType(boolean extended, type tp) {
		this.extended = extended;
		this.tp = tp;
	}

}
