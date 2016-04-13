package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSArrayAccessStartData extends CSFlowLineData{
	
	public CSArrayAccessStartData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.ArrayAccessBlcok)
		{
			throw new CodeSynthesisException("array access does not in right block.");
		}
		signals.pop();
	}
	
}