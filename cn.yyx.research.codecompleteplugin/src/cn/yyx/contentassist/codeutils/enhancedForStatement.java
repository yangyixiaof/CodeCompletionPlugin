package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class enhancedForStatement extends statement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public enhancedForStatement(type rt, referedExpression rexp) {
		this.tp = rt;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enhancedForStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enhancedForStatement)
		{
			return 0.4 + 0.6*(0.6*tp.Similarity(((enhancedForStatement) t).tp) + 0.4*(rexp.Similarity(((enhancedForStatement) t).rexp)));
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
		tp.HandleCodeSynthesis(squeue, expected, handler, tpcs, null);
		tpcs.setMaytypereplacer(true);
		tpcs.setPrefix("for (");
		tpcs.setPostfix(" et : ");
		CSNode rexpcs = new CSNode(CSNodeType.HalfFullExpression);
		rexp.HandleCodeSynthesis(squeue, expected, handler, rexpcs, null);
		rexpcs.setPrefix("");
		rexpcs.setPostfix(") {\n}");
		return false;
	}
	
}