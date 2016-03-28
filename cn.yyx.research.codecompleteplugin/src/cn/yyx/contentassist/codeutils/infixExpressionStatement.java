package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class infixExpressionStatement extends expressionStatement{
	
	referedExpression lexp = null;
	referedExpression rexp = null;
	String optr = null;
	
	public infixExpressionStatement(referedExpression lexp, String optr, referedExpression rexp) {
		this.lexp = lexp;
		this.rexp = rexp;
		this.optr = optr;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return 0.5 + 0.5*(0.5*(rexp.Similarity(((infixExpressionStatement) t).rexp)) + 0.5*(lexp.Similarity(((infixExpressionStatement) t).lexp)));
			}
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
		CSNode ltcs = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = lexp.HandleCodeSynthesis(squeue, expected, handler, ltcs, ai);
		if (conflict)
		{
			return true;
		}
		ltcs.setPostfix(optr);
		squeue.add(ltcs);
		CSNode rtcs = new CSNode(CSNodeType.HalfFullExpression);
		conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, rtcs, ai);
		if (conflict)
		{
			return true;
		}
		squeue.add(rtcs);
		return false;
	}
	
}