package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class instanceofExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	type type = null;
	
	public instanceofExpressionStatement(referedExpression rexp, type type) {
		this.rexp = rexp;
		this.type = type;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return 0.4 + 0.6*(0.5*rexp.Similarity(((instanceofExpressionStatement) t).rexp) + 0.5*type.Similarity(((instanceofExpressionStatement) t).type));
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
		CSNode tpcs = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = type.HandleCodeSynthesis(squeue, expected, handler, tpcs, ai);
		if (conflict)
		{
			return true;
		}
		tpcs.setPrefix("(");
		tpcs.setPostfix(")");
		squeue.add(tpcs);
		CSNode recs = new CSNode(CSNodeType.HalfFullExpression);
		conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, recs, ai);
		if (conflict)
		{
			return true;
		}
		squeue.add(recs);
		return false;
	}
	
}