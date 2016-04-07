package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineQueue {
	
	private FlowLineNode<CSFlowLineData> last = null;
	
	/*protected CSFlowLineQueue() {
		// only can be invoked from subclass.
	}*/
	
	public CSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		this.setLast(last);
	}
	
	public SynthesisHandler GetLastHandler()
	{
		return getLast().getData().getHandler();
	}
	
	public int GenerateNewNodeId()
	{
		CheckUtil.CheckNotNull(getLast(), "the 'last' member of CSFlowLineQueue is null, serious error, the system will exit.");
		return getLast().getData().getSynthesisCodeManager().GenerateNextLevelId();
	}

	public FlowLineNode<CSFlowLineData> getLast() {
		return last;
	}

	public void setLast(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	public void SetLastHasHole()
	{
		last.getData().setHashole(true);
	}

	public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int signal) {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			Integer struct = tmp.getData().getStructsignal();
			if (struct != null && struct == signal)
			{
				return tmp;
			}
		}
		return null;
	}
	
}