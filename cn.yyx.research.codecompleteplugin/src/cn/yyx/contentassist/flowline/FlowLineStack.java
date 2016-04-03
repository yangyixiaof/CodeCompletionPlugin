package cn.yyx.contentassist.flowline;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
}