package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSForExpOverProperty extends CSExtraProperty {
	
	public CSForExpOverProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("for ini over does not have common for prefixed.");
		}
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.CommonForUpdWaitingOver)
		{
			throw new CodeSynthesisException("for ini over does not have common for prefixed.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.CommonForExpWaitingOver);
	}
	
}