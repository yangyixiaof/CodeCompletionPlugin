package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		result.setContenttype(CSNodeType.SymbolMark);
		result.AddOneData(value+"", null);
		return false;
	}

}
