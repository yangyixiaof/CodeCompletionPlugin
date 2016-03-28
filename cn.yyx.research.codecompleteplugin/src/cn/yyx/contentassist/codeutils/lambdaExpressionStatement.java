package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class lambdaExpressionStatement extends statement{
	
	typeList tlist = null;
	
	public lambdaExpressionStatement(typeList tlist) {
		this.tlist = tlist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			return 0.4 + 0.6*(tlist.Similarity(((lambdaExpressionStatement) t).tlist));
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
		CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
		if (tlist == null)
		{
			cs.AddOneData("()->", null);
		}
		else
		{
			boolean conflict = tlist.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			cs.setPrefix("(");
			cs.setPostfix(")->");
		}
		squeue.add(cs);
		return false;
	}
	
}