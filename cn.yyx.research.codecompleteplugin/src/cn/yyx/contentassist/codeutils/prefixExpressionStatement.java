package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class prefixExpressionStatement extends expressionStatement{
	
	String optr = null;
	referedExpression rexp = null;
	
	public prefixExpressionStatement(String optr, referedExpression rexp) {
		this.optr = optr;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof prefixExpressionStatement)
		{
			if (optr.equals(((prefixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof prefixExpressionStatement)
		{
			if (optr.equals(((prefixExpressionStatement) t).optr))
			{
				return 1;
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
		CSNode cs = new CSNode(CSNodeType.WholeStatement);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
		cs.setPrefix(optr);
		squeue.add(cs);
		return conflict;
	}
	
}