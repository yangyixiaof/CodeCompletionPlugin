package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class partialEndArrayAccessStatement extends statement{
	
	expressionStatement es = null;
	
	public partialEndArrayAccessStatement(expressionStatement es) {
		this.es = es;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return es.CouldThoughtSame(((partialEndArrayAccessStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((partialEndArrayAccessStatement) t).es));
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
		TODO
		return false;
	}
	
}