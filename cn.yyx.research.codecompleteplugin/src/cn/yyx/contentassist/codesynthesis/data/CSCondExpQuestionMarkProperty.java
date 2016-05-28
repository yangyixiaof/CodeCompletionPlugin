package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSCondExpQuestionMarkProperty extends CSExtraProperty {
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("cond colon mark does not have common for prefixed.");
		}
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.ConditionExpressionColon)
		{
			throw new CodeSynthesisException("cond colon mark does not have common for prefixed.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.ConditionExpressionQuestion);
	}
	
}