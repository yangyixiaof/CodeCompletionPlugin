package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.Sentence;

public class CodeSynthesisFlowLine extends FlowLines<CSFlowLineData> {
	
	Map<Integer, FlowLineNode<Sentence>> headsconnect = new TreeMap<Integer, FlowLineNode<Sentence>>();
	
	public CodeSynthesisFlowLine() {
	}
	
	// only for First Level
	public void AddToFirstLevel(FlowLineNode<CSFlowLineData> addnode, FlowLineNode<Sentence> prenode)
	{
		CSFlowLineData data = addnode.getData();
		Integer id = data.getId();
		headsconnect.put(id, prenode);
		AddToNextLevel(addnode, null);
	}
	
	public FlowLineNode<Sentence> GetConnect(Integer tid)
	{
		return headsconnect.get(tid);
	}
	
	public List<String> GetSynthesisedCode()
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
	}
	
}