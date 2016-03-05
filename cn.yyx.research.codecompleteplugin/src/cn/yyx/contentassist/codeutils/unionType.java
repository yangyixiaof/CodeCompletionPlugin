package cn.yyx.contentassist.codeutils;

import java.util.List;

public class unionType extends type{
	
	List<type> tps = null;
	
	public unionType(List<type> tps) {
		this.tps = tps;
	}

}
