package cn.yyx.contentassist.commonutils;

import java.util.List;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class GenBlock {
	
	private int idx = 0;
	private List<FlowLineNode<CSFlowLineData>> gennodes = null;
	
	public GenBlock(List<FlowLineNode<CSFlowLineData>> gennodes) {
		this.setGennodes(gennodes);
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public List<FlowLineNode<CSFlowLineData>> getGennodes() {
		return gennodes;
	}

	public void setGennodes(List<FlowLineNode<CSFlowLineData>> gennodes) {
		this.gennodes = gennodes;
	}
	
}