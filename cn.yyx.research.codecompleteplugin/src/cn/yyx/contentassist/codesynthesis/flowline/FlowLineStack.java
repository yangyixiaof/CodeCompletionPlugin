package cn.yyx.contentassist.codesynthesis.flowline;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
}