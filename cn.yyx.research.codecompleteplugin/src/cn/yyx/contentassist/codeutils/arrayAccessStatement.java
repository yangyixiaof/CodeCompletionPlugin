package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class arrayAccessStatement extends statement{
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	boolean accessEnd = false;
	
	public arrayAccessStatement(referedExpression rarr, referedExpression rexp, boolean accessEnd) {
		this.rarr = rarr;
		this.rexp = rexp;
		this.accessEnd = accessEnd;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayAccessStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayAccessStatement)
		{
			return 0.4 + 0.6*(rarr.Similarity(((arrayAccessStatement) t).rarr) + rexp.Similarity(((arrayAccessStatement) t).rexp));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode smt = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = false;
		CSNode csarr = new CSNode(CSNodeType.TempUsed);
		conflict = rarr.HandleCodeSynthesis(squeue, expected, handler, csarr, null);
		if (conflict)
		{
			return true;
		}
		
		TypeCheck iit = new TypeCheck();
		iit.setExpreturntype("java.lang.Integer");
		iit.setExpreturntypeclass(Integer.class);
		
		expected.push(iit);
		CSNode cidx = new CSNode(CSNodeType.TempUsed);
		cidx.setPrefix("[");
		conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cidx, null);
		if (conflict)
		{
			return true;
		}
		if (accessEnd)
		{
			cidx.setPostfix("]");
		}
		squeue.add(smt);
		expected.pop();
		return false;
	}
	
}