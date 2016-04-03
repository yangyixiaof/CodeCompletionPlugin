package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.flowline.CSFlowLineData;
import cn.yyx.contentassist.flowline.FlowLineNode;

public class VirtualCSFlowLineQueue extends CSFlowLineQueue{
	
	public VirtualCSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		super(last);
	}
	
}