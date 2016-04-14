package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSMethodSignalHandleResult {
	
	private FlowLineNode<CSFlowLineData> farem = null;
	private int faremused = 0;
	
	public CSMethodSignalHandleResult(FlowLineNode<CSFlowLineData> farem, int faremused) {
		this.setFarem(farem);
		this.setFaremused(faremused);
	}

	public FlowLineNode<CSFlowLineData> getFarem() {
		return farem;
	}

	public void setFarem(FlowLineNode<CSFlowLineData> farem) {
		this.farem = farem;
	}

	public int getFaremused() {
		return faremused;
	}

	public void setFaremused(int faremused) {
		this.faremused = faremused;
	}
	
}