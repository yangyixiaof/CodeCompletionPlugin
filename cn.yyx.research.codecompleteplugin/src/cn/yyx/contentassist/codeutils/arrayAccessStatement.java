package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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
	public void HandleOverSignal(Stack<Integer> cstack) {
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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		boolean conflict = false;
		StringBuilder fin = new StringBuilder("");
		conflict = rarr.HandleCodeSynthesis(squeue, handler, fin, null);
		if (conflict)
		{
			return true;
		}
		fin.append("[");
		conflict = rexp.HandleCodeSynthesis(squeue, handler, fin, null);
		if (conflict)
		{
			return true;
		}
		if (accessEnd)
		{
			fin.append("]");
		}
		squeue.add(fin.toString());
		return false;
	}
	
}