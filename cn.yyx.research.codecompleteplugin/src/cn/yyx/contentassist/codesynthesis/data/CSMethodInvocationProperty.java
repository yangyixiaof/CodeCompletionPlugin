package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSMethodInvocationProperty extends CSExtraProperty {
	
	private boolean hasem = false;
	 
	public CSMethodInvocationProperty(boolean hasem) {
		this.setHasem(hasem);
	}
	
	public boolean isHasem() {
		return hasem;
	}
	
	public void setHasem(boolean hasem) {
		this.hasem = hasem;
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		signals.push(DataStructureSignalMetaInfo.MethodInvocation);
	}
	
}