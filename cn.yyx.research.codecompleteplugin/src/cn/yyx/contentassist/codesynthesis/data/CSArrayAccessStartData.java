package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

public class CSArrayAccessStartData extends CSFlowLineData{
	
	public CSArrayAccessStartData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) {
		signals.push(DataStructureSignalMetaInfo.ArrayAccessBlcok);
	}
	
}