package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableDeclarationData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineQueue {
	
	private FlowLineNode<CSFlowLineData> last = null;
	
	/*protected CSFlowLineQueue() {
		// only can be invoked from subclass.
	}*/
	// private String recenttype = null;
	// private Class<?> recenttypeclass = null;
	
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

	public FlowLineNode<CSFlowLineData> SearcheForRecentVariableDeclaredNode() {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}

	public List<String> SearchForVariableDeclareHolderNames() {
		List<String> result = new LinkedList<String>();
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableHolderData)
			{
				result.add(0, ((CSVariableHolderData) tmpdata).getVarname());
			}
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				return result;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}

	public FlowLineNode<CSFlowLineData> BackSearchForSpecialClass(Class<?> cls) {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata.getClass().equals(cls))
			{
				return tmp;
			}
		}
		return null;
	}

	/*public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int signal) {
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

	public Class<?> getRecenttypeclass() {
		return recenttypeclass;
	}

	public void setRecenttypeclass(Class<?> recenttypeclass) {
		this.recenttypeclass = recenttypeclass;
	}

	public String getRecenttype() {
		return recenttype;
	}

	public void setRecenttype(String recenttype) {
		this.recenttype = recenttype;
	}*/
	
}