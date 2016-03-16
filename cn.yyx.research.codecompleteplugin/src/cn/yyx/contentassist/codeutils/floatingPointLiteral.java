package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisHelper;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class floatingPointLiteral extends numberLiteral{
	
	double value = -1;
	
	public floatingPointLiteral(double parseDouble) {
		this.value = parseDouble;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof floatingPointLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof floatingPointLiteral)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		return CodeSynthesisHelper.HandleRawTextSynthesis(value+"", squeue, handler, result, null);
	}
	
}