package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSPrProperty extends CSExtraProperty {

	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		Integer top = signals.peek();
		if (top == null || top != DataStructureSignalMetaInfo.MethodInvocation || top != DataStructureSignalMetaInfo.MethodPs)
		{
			throw new CodeSynthesisException("When handling pr, the top of stack is not MethodInvocation or MethodPs.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.MethodPr);
	}
	
}