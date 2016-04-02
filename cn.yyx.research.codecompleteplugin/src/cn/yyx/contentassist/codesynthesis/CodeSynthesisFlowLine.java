package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.statement;

public class CodeSynthesisFlowLine {
	
	CSParLineNode root = null;
	
	public CodeSynthesisFlowLine() {
	}
	
	public boolean ExtendOneSentence(statement smt)
	{
		// TODO
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		CodeSynthesisFlowLine o = (CodeSynthesisFlowLine) super.clone();
		
		return o;
	}
	
	public List<String> GetSynthesisedCode()
	{
		LinkedList<String> res = new LinkedList<String>();
		if (root != null)
		{
			CSParLineNode tmp = root;
			while (tmp != null)
			{
				res.add(tmp.getData());
				tmp = tmp.getSilbnext();
			}
		}
		return res;
	}
	
}