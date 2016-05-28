package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSMethodInvocationData extends CSFlowLineData{
	
	private boolean hasem = false;
	 
	public CSMethodInvocationData(boolean hasem, CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
		this.setHasem(hasem);
		this.setCsep(dt.getCsep());
		this.setScm(dt.getSynthesisCodeManager());
		this.setExtraData(dt.getExtraData());
	}
	
	public boolean isHasem() {
		return hasem;
	}
	
	public void setHasem(boolean hasem) {
		this.hasem = hasem;
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		super.HandleStackSignal(signals);
		signals.push(DataStructureSignalMetaInfo.MethodInvocation);
	}
	
}