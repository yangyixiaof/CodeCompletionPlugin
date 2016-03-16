package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class forExpOverStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forExpOverStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forExpOverStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		int sttop = cstack.peek();
		if (sttop == PredictMetaInfo.CommonForInitWaitingOver)
		{
			cstack.pop();
		}
		else
		{
			return true;
		}
		cstack.push(PredictMetaInfo.CommonForExpWaitingOver);
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		squeue.add(";", true, 0);
		return false;
	}
	
}