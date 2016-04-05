package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class preExist extends referedExpression{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof preExist)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof preExist)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = squeue.Pop();
		result.SetCSNodeContent(cs);
		return false;
	}

}
