package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSLeftParenInfoNode extends CSParLineNode{
	
	int times = -1;
	
	public CSLeftParenInfoNode(int times, SynthesisHandler handler) {
		super(handler);
		this.times = times;
	}
	
}