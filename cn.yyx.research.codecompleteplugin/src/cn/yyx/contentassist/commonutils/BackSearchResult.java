package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class BackSearchResult {
	
	private boolean selfisneeded = false;
	private FlowLineNode<CSFlowLineData> cnode = null;
	
	public BackSearchResult(boolean selfisneeded, FlowLineNode<CSFlowLineData> result) {
		this.setSelfisneeded(selfisneeded);
		this.setCnode(result);
	}

	public boolean isSelfisneeded() {
		return selfisneeded;
	}

	public void setSelfisneeded(boolean selfisneeded) {
		this.selfisneeded = selfisneeded;
	}
	
	public boolean isValid()
	{
		if (selfisneeded)
		{
			return true;
		}
		if (getCnode() != null)
		{
			return true;
		}
		return false;
	}

	public FlowLineNode<CSFlowLineData> getCnode() {
		return cnode;
	}

	public void setCnode(FlowLineNode<CSFlowLineData> cnode) {
		this.cnode = cnode;
	}
	
}