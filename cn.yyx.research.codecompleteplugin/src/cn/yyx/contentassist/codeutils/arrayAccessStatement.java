package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class arrayAccessStatement extends statement{
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	
	public arrayAccessStatement(referedExpression rarr, referedExpression rexp) {
		this.rarr = rarr;
		this.rexp = rexp;
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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		rarr.HandleCodeSynthesis(squeue, handler, result, ai);
		
		return false;
	}
	
}