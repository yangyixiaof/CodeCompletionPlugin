package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineQueue {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	/*protected CSFlowLineQueue() {
		// only can be invoked from subclass.
	}*/
	
	public CSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	public SynthesisHandler GetLastHandler()
	{
		return last.getData().getHandler();
	}
	
	public int GenerateNewNodeId()
	{
		CheckUtil.CheckNotNull(last, "the 'last' member of CSFlowLineQueue is null, serious error, the system will exit.");
		return last.getData().getScm().GenerateNextLevelId();
	}
	
	
	
}