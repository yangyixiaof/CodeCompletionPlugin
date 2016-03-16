package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class forIniOverStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forIniOverStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forIniOverStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		int sttop = cstack.peek();
		if (sttop == PredictMetaInfo.CommonForKindWaitingOver)
		{
			cstack.pop();
		}
		else
		{
			return true;
		}
		cstack.push(PredictMetaInfo.CommonForInitWaitingOver);
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		squeue.add(";", true, 0);
		return false;
	}
	
}