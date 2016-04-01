package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSEnterParamInfoNode extends CSParLineNode{
	
	int times = -1;
	int usedtimes = -1;
	
	public CSEnterParamInfoNode(int times, SynthesisHandler handler) {
		super(handler);
		this.times = times;
	}
	
}