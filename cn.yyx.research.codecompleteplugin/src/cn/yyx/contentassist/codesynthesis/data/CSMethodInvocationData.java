package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSMethodInvocationData extends CSFlowLineData{
	
	private FlowLineNode<CSFlowLineData> mostfarem = null;
	private boolean hasem = false;
	
	public CSMethodInvocationData(boolean hasem, CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
		this.setHasem(hasem);
	}
	
	public FlowLineNode<CSFlowLineData> getMostfarem() {
		return mostfarem;
	}
	
	public void setMostfarem(FlowLineNode<CSFlowLineData> mostfarem) {
		this.mostfarem = mostfarem;
	}
	
	public boolean isHasem() {
		return hasem;
	}
	
	public void setHasem(boolean hasem) {
		this.hasem = hasem;
	}
	
}