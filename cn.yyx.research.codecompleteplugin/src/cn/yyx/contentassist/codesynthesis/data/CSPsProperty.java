package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSPsProperty extends CSExtraProperty {
	
	private static CSExtraProperty cd = new CSPsProperty();
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		Integer top = signals.peek();
		if (top == null || top != DataStructureSignalMetaInfo.MethodInvocation || top != DataStructureSignalMetaInfo.MethodPs)
		{
			throw new CodeSynthesisException("When handling ps, the top of stack is not MethodInvocation or MethodPs.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.MethodPs);
	}
	
	public static CSExtraProperty GetInstance()
	{
		return cd;
	}
	
}