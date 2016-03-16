package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class castExpressionStatement extends expressionStatement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public castExpressionStatement(type tp, referedExpression rexp) {
		this.tp = tp;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return 0.4 + 0.6*(tp.Similarity(((castExpressionStatement) t).tp) + rexp.Similarity(((castExpressionStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder fin = new StringBuilder("");
		StringBuilder tpsb = new StringBuilder("");
		boolean conflict = tp.HandleCodeSynthesis(squeue, handler, tpsb, null);
		if (conflict)
		{
			return true;
		}
		fin.append("("+tpsb.toString()+")");
		StringBuilder resb = new StringBuilder("");
		conflict = rexp.HandleCodeSynthesis(squeue, handler, resb, null);
		if (conflict)
		{
			return true;
		}
		fin.append(resb);
		squeue.add(fin.toString());
		return false;
	}
	
}