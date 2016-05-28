package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableDeclarationData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.VariableHT;

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
	
	public FlowLineNode<CSFlowLineData> SearcheForRecentVHolderNode() {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				break;
			}
			if (tmpdata instanceof CSVariableHolderData)
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
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

	/*public FlowLineNode<CSFlowLineData> BackSearchForSpecialClass(Class<?> cls) throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		return BackSearchForSpecialClass(cls, signals);
	}*/
	
	public FlowLineNode<CSFlowLineData> BackSearchForHead()
	{
		FlowLineNode<CSFlowLineData> tmp = last;
		FlowLineNode<CSFlowLineData> pretmp = null;
		while (tmp != null)
		{
			pretmp = tmp;
			tmp = tmp.getPrev();
		}
		return pretmp;
	}
	
	public FlowLineNode<CSFlowLineData> BackSearchForSpecialClass(Class<?> cls,
			Stack<Integer> signals) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			tmpdata.HandleStackSignal(signals);
			if (tmpdata.HasSpecialProperty(cls) && signals.isEmpty())
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}
	
	public FlowLineNode<CSFlowLineData> BackSearchForTheNextOfSpecialClass(Class<?> cls,
			Stack<Integer> signals) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = last;
		FlowLineNode<CSFlowLineData> tmpnext = null;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			tmpdata.HandleStackSignal(signals);
			if (tmpdata.HasSpecialProperty(cls) && signals.isEmpty())
			{
				return tmpnext;
			}
			tmpnext = tmp;
			tmp = tmp.getPrev();
		}
		return null;
	}
	
	public VariableHT BackSearchForLastIthVariableHolderAndTypeDeclaration(int off) {
		String vhtp = null;
		String vhne = null;
		FlowLineNode<CSFlowLineData> tmp = last;
		String recentvhne = null;
		int totalvh = 0;
		// skip very close var holder.
		// off++; TODO
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			// TODO remove
			/*if (tmpdata.HasSpecialProperty(CSForIniOverProperty.class))
			{
				off--;
			}*/
			if (tmpdata instanceof CSVariableHolderData)
			{
				totalvh++;
				if (off >= 0)
				{
					recentvhne = ((CSVariableHolderData)tmpdata).getVarname();
					off--;
					if (off < 0)
					{
						vhne = recentvhne;
					}
				}
			}
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				vhtp = ((CSVariableDeclarationData)tmpdata).getData();
				break;
			}
			tmp = tmp.getPrev();
		}
		if (vhtp != null && totalvh > 0)
		{
			return new VariableHT(vhtp, vhne, totalvh);
		}
		//if ((vhtp == null && vhne != null) || (vhtp != null && vhne == null))
		//{
		//	throw new Error("Strange, has declarations but no holders or has holders but no declarations.");
		//}
		return new VariableHT();
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