package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisHelper;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class assignmentOperator extends OneCode{
	
	String optr = null;
	
	public assignmentOperator(String text) {
		this.optr = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof assignmentOperator)
		{
			if (optr.equals(((assignmentOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof assignmentOperator)
		{
			if (optr.equals(((assignmentOperator) t).optr))
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		CodeSynthesisHelper.HandleRawTextSynthesis(optr, squeue, handler, result, ai);
		return false;
	}
	
}