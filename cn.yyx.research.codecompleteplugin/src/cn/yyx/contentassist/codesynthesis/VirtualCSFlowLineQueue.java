package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class VirtualCSFlowLineQueue extends CSFlowLineQueue{
	
	public VirtualCSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		super(last);
	}
	
}