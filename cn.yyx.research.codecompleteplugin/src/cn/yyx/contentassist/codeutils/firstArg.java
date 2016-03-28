package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class firstArg implements OneCode {
	
	classInvoke ci = null;
	
	public firstArg(classInvoke ci) {
		this.ci = ci;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof firstArg)
		{
			if (ci.CouldThoughtSame(((firstArg) t).ci))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof firstArg)
		{
			return 0.2+0.8*(ci.Similarity(((firstArg)t).ci));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result,
			AdditionalInfo ai) {
		if (ci != null)
		{
			return ci.HandleCodeSynthesis(squeue, expected, handler, result, ai);
		}
		// all null means error.
		return true;
	}
	
}