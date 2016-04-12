package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.Sentence;

public class CodeSynthesisFlowLines extends FlowLines<CSFlowLineData> {
	
	Map<String, FlowLineNode<Sentence>> headsconnect = new TreeMap<String, FlowLineNode<Sentence>>();
	
	public CodeSynthesisFlowLines() {
	}
	
	// only for First Level
	public void AddToFirstLevel(FlowLineNode<CSFlowLineData> addnode, FlowLineNode<Sentence> prenode)
	{
		CSFlowLineData data = addnode.getData();
		String id = data.getId();
		headsconnect.put(id, prenode);
		AddToNextLevel(addnode, null);
	}
	
	public FlowLineNode<Sentence> GetConnect(String tid)
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