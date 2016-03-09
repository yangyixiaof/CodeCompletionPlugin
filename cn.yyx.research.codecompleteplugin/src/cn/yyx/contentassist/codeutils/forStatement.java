package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;

public class forStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		int sttop = cstack.peek();
		if (sttop == PredictMetaInfo.AllKindWaitingOver)
		{
			cstack.pop();
		}
		cstack.push(PredictMetaInfo.CommonForKindWaitingOver);
	}

}
