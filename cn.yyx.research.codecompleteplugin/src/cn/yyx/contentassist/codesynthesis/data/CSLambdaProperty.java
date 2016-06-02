package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSLambdaProperty extends CSExtraProperty {
	
	public CSLambdaProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}

	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (signals.size() == 0)
		{
			throw new CodeSynthesisException("Lambda block error.");
		}
		int sig = signals.peek();
		if (sig != DataStructureSignalMetaInfo.LambdaExpression)
		{
			throw new CodeSynthesisException("Lambda block error.");
		}
		signals.pop();
	}
	
}