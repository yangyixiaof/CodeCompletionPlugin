package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSCondExpColonMarkProperty extends CSExtraProperty {
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		signals.push(DataStructureSignalMetaInfo.ConditionExpressionColon);
	}
	
}