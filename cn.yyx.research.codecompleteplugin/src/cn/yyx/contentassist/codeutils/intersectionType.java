package cn.yyx.contentassist.codeutils;

import java.util.List;

public class intersectionType extends type{
	
	List<type> tps = null;
	
	public intersectionType(List<type> tps) {
		this.tps = tps;
	}

}
