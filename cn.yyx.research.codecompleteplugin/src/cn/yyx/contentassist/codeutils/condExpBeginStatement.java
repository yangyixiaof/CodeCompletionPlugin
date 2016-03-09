package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;

public class condExpBeginStatement extends statement{
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof condExpBeginStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof condExpBeginStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		cstack.push(PredictMetaInfo.ConditionExpressionOver);
	}
	
}