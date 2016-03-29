package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class unaryOperator implements OneCode{
	
	String optr = null;
	
	public unaryOperator(String text) {
		this.optr = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof unaryOperator)
		{
			if (optr.equals(((unaryOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof unaryOperator)
		{
			double prob = 0;
			if (optr.equals(((unaryOperator) t).optr))
			{
				prob = 1;
			}
			return 0.5 + 0.5 * prob;
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		result.setContenttype(CSNodeType.SymbolMark);
		result.AddOneData(optr, null);
		return false;
	}
	
}