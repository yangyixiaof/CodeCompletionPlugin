package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSLambdaProperty extends CSExtraProperty {
	
	private boolean overed = false;
	
	public CSLambdaProperty(boolean overed, CSExtraProperty csepnext) {
		super(csepnext);
		this.setOvered(overed);
	}

	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		if (isOvered())
		{
			return;
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

	public boolean isOvered() {
		return overed;
	}

	public void setOvered(boolean overed) {
		this.overed = overed;
	}
	
}