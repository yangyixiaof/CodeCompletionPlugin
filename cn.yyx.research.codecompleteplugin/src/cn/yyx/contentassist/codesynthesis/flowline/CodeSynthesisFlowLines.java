package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;

public class CodeSynthesisFlowLines extends FlowLines<CSFlowLineData> {
	
	private Map<String, FlowLineNode<Sentence>> headsconnect = new TreeMap<String, FlowLineNode<Sentence>>();
	
	private List<FlowLineNode<CSFlowLineData>> overcodesynthesises = new LinkedList<FlowLineNode<CSFlowLineData>>();
	private int validovers = 0;
	
	Sentence sete = null;
	
	public CodeSynthesisFlowLines() {
		opflag = true;
	}
	
	public void AddCodeSynthesisOver(FlowLineNode<CSFlowLineData> finalover, Sentence pred)
	{
		overcodesynthesises.add(finalover);
		if (sete != pred)
		{
			validovers++;
		}
		sete = pred;
	}
	
	// only for First Level
	public void AddToFirstLevel(FlowLineNode<CSFlowLineData> addnode, FlowLineNode<Sentence> prenode)
	{
		CSFlowLineData data = addnode.getData();
		String id = data.getId();
		getHeadsconnect().put(id, prenode);
		AddToNextLevel(addnode, null);
	}
	
	public FlowLineNode<Sentence> GetConnect(String tid)
	{
		return getHeadsconnect().get(tid);
	}
	
	public List<String> GetSynthesisOverCode()
	{
		List<String> res = new LinkedList<String>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = overcodesynthesises.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			res.add(fln.getData().getData());
		}
		return res;
	}
	
	@Override
	protected void TempTailOperation(FlowLineNode<CSFlowLineData> addnode) {
	}
	
	@Override
	protected void CheckOperationPermit() {
	}
	
	@Override
	public void BeginOperation()
	{
	}
	
	@Override
	public void EndOperation()
	{
	}
	
	/*public List<String> GetSynthesisedCode()
	{
		LinkedList<String> res = new LinkedList<String>();
		FlowLineNode<CSFlowLineData> head = getHeads();
		FlowLineNode<CSFlowLineData> tmp = head;
		while (tmp != null)
		{
			CSFlowLineData data = tmp.getData();
			SynthesisCodeManager scm = data.getSynthesisCodeManager();
			res.addAll(scm.GetSynthesisedCode());
			tmp = head.getSilbnext();
		}
		return res;
	}*/

	public Map<String, FlowLineNode<Sentence>> getHeadsconnect() {
		return headsconnect;
	}

	public void setHeadsconnect(Map<String, FlowLineNode<Sentence>> headsconnect) {
		this.headsconnect = headsconnect;
	}

	public int getValidovers() {
		return validovers;
	}

	public void setValidovers(int validovers) {
		this.validovers = validovers;
	}
	
}