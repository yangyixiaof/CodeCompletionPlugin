package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSMethodInvocationData extends CSFlowLineData{
	
	// private FlowLineNode<CSFlowLineData> mostfarem = null;
	// private int mostfarused = 0;
	private boolean hasem = false;
	
	// FlowLineNode<CSFlowLineData> mostfarem, int mostfarused, 
	public CSMethodInvocationData(boolean hasem, CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
		this.setHasem(hasem);
		// this.setMostfarused(mostfarused);
		// this.setMostfarem(mostfarem);
	}
	
	/*public FlowLineNode<CSFlowLineData> getMostfarem() {
		return mostfarem;
	}
	
	public void setMostfarem(FlowLineNode<CSFlowLineData> mostfarem) {
		this.mostfarem = mostfarem;
	}*/
	
	public boolean isHasem() {
		return hasem;
	}
	
	public void setHasem(boolean hasem) {
		this.hasem = hasem;
	}

	/*public int getMostfarused() {
		return mostfarused;
	}

	public void setMostfarused(int mostfarused) {
		this.mostfarused = mostfarused;
	}*/
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		super.HandleStackSignal(signals);
		signals.push(DataStructureSignalMetaInfo.MethodInvocation);
	}
	
}