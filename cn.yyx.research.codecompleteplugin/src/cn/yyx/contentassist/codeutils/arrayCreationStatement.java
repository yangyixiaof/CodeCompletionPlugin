package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class arrayCreationStatement extends statement{
	
	type tp = null;
	
	public arrayCreationStatement(type rt) {
		this.tp = rt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			if (tp.CouldThoughtSame(((arrayCreationStatement) t).tp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayCreationStatement)
		{
			return 0.4 + 0.6*(tp.Similarity(((arrayCreationStatement) t).tp));
		}
		return 0;
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ttp = new CSNode(CSNodeType.HalfFullExpression);
		tp.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
		ttp.setPrefix("new ");
		squeue.add(ttp);
		return false;
	}
	
}