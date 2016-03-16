package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisHelper;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class booleanLiteral extends literal{
	
	boolean value = false;
	
	public booleanLiteral(boolean parseBoolean) {
		this.value = parseBoolean;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof booleanLiteral)
		{
			if (value == ((booleanLiteral)t).value)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof booleanLiteral)
		{
			if (value == ((booleanLiteral)t).value)
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		CodeSynthesisHelper.HandleRawTextSynthesis(value+"", squeue, handler, result, ai);
		return false;
	}

}
