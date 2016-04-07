package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSEnterParamInfoData;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class enterMethodParamStatement extends statement{
	
	int times = -1;
	
	public enterMethodParamStatement(int times) {
		this.times = times;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enterMethodParamStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enterMethodParamStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSEnterParamInfoData cs = new CSEnterParamInfoData(times);
		squeue.add(cs);
		return false;
	}
	
}