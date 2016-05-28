package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSCondExpBeginProperty extends CSExtraProperty {
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("cond exp begin does not have common for prefixed.");
		}
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.ConditionExpressionQuestion)
		{
			throw new CodeSynthesisException("cond exp begin does not have common for prefixed.");
		}
		signals.pop();
	}
	
}