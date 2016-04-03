package cn.yyx.contentassist.flowline;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.statement;

public class CodeSynthesisFlowLine extends FlowLines<CSFlowLineData> {
	
	public CodeSynthesisFlowLine() {
	}
	
	public boolean ExtendOneSentence(statement smt)
	{
		// TODO
		return false;
	}
	
	public List<String> GetSynthesisedCode()
	{
		LinkedList<String> res = new LinkedList<String>();
		FlowLineNode<CSFlowLineData> head = getHeads();
		FlowLineNode<CSFlowLineData> tmp = head;
		while (tmp != null)
		{
			CSFlowLineData data = tmp.getData();
			SynthesisCodeManager scm = data.getScm();
			res.addAll(scm.GetSynthesisedCode());
			tmp = head.getSilbnext();
		}
		return res;
	}
	
}