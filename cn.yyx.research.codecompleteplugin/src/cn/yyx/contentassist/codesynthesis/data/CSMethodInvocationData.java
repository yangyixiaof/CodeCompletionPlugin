package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSMethodInvocationData extends CSFlowLineData{
	
	private FlowLineNode<CSFlowLineData> mostfarem = null;
	private int emusedtimes = 0;
	
	public CSMethodInvocationData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}

	public FlowLineNode<CSFlowLineData> getMostfarem() {
		return mostfarem;
	}

	public void setMostfarem(FlowLineNode<CSFlowLineData> mostfarem) {
		this.mostfarem = mostfarem;
	}

	public int getEmusedtimes() {
		return emusedtimes;
	}

	public void setEmusedtimes(int emusedtimes) {
		this.emusedtimes = emusedtimes;
	}
	
}