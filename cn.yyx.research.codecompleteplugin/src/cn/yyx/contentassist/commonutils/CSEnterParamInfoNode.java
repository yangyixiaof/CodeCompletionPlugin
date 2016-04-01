package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.CSNode;

public class CSEnterParamInfoNode extends CSNode{
	
	int times = -1;
	int usedtimes = -1;
	
	public CSEnterParamInfoNode(int times) {
		super(CSNodeType.HelpInfo);
		this.times = times;
	}
	
}