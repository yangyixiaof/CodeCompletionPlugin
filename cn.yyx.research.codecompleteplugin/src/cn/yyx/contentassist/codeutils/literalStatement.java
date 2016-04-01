package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class literalStatement extends expressionStatement{
	
	literal lt = null;
	
	public literalStatement(literal lt) {
		this.lt = lt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.CouldThoughtSame(((literalStatement) t).lt);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.Similarity(((literalStatement) t).lt);
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
		CSNode fcs = new CSNode(CSNodeType.HalfFullExpression);
		lt.HandleCodeSynthesis(squeue, expected, handler, fcs, ai);
		squeue.add(fcs);
		return false;
	}
	
}