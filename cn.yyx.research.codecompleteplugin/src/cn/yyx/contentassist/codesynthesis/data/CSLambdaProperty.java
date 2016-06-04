package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSLambdaProperty extends CSExtraProperty {
	
	boolean overed = false;
	
	public CSLambdaProperty(boolean overed, CSExtraProperty csepnext) {
		super(csepnext);
		this.overed = overed;
	}

	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (overed)
		{
			// TODO
			// return;
		}
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