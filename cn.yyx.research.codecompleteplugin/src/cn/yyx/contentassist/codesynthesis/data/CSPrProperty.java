package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSPrProperty extends CSExtraProperty {
	
	public CSPrProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("When handling pr, the top of stack is not MethodInvocation or MethodPs.");
		}
		Integer top = signals.peek();
		if (top == null || (top != DataStructureSignalMetaInfo.MethodInvocation && top != DataStructureSignalMetaInfo.MethodPs))
		{
			throw new CodeSynthesisException("When handling pr, the top of stack is not MethodInvocation or MethodPs.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.MethodPr);
	}
	
}