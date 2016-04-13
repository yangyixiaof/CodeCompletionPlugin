package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

public class CSArrayAccessEndData extends CSFlowLineData{

	public CSArrayAccessEndData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) {
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.ArrayAccessBlcok)
		{
			signals.pop();
		}
	}
	
}