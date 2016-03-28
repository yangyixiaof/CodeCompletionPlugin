package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class newClassInvoke extends classInvoke {
	
	referedExpression rexp = null;
	
	public newClassInvoke(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		if (rexp == null)
		{
			TODO
		}
		return false;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof newClassInvoke)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof newClassInvoke)
		{
			return 0.3+0.7*(rexp.Similarity(((newClassInvoke) t).rexp));
		}
		return 0;
	}

}
