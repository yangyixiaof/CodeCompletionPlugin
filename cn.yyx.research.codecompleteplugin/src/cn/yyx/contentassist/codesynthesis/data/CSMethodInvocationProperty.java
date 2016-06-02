package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSMethodInvocationProperty extends CSExtraProperty {
	
	private boolean hasem = false;
	 
	public CSMethodInvocationProperty(boolean hasem, CSExtraProperty csepnext) {
		super(csepnext);
		this.setHasem(hasem);
	}
	
	public boolean isHasem() {
		return hasem;
	}
	
	public void setHasem(boolean hasem) {
		this.hasem = hasem;
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		signals.push(DataStructureSignalMetaInfo.MethodInvocation);
	}
	
}