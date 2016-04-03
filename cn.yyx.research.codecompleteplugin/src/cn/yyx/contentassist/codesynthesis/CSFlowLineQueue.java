package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.flowline.CSFlowLineData;
import cn.yyx.contentassist.flowline.FlowLineNode;

public class CSFlowLineQueue {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	/*protected CSFlowLineQueue() {
		// only can be invoked from subclass.
	}*/
	
	public CSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
}