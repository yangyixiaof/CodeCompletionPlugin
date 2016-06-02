package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSPsProperty extends CSExtraProperty {
	
	public CSPsProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("When handling ps, the top of stack is not MethodInvocation or MethodPs.");
		}
		Integer top = signals.peek();
		if (top == null || (top != DataStructureSignalMetaInfo.MethodInvocation && top != DataStructureSignalMetaInfo.MethodPs))
		{
			throw new CodeSynthesisException("When handling ps, the top of stack is not MethodInvocation or MethodPs.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.MethodPs);
	}
	
}