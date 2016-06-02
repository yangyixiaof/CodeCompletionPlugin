package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public abstract class CSExtraProperty {
	
	private CSExtraProperty csepnext = null;
	
	public CSExtraProperty(CSExtraProperty csepnext) {
		this.setCsepnext(csepnext);
	}
	
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException
	{
		if (csepnext != null)
		{
			csepnext.HandleStackSignal(signals);
		}
		HandleStackSignalDetail(signals);
	}
	
	public abstract void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException;

	public CSExtraProperty getCsepnext() {
		return csepnext;
	}

	public void setCsepnext(CSExtraProperty csepnext) {
		this.csepnext = csepnext;
	}
	
}