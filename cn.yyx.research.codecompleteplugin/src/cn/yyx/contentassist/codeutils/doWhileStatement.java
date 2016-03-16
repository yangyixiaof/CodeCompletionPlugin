package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class doWhileStatement extends statement{
	
	referedExpression rexp = null;
	
	public doWhileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((doWhileStatement) t).rexp));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder resb = new StringBuilder("");
		rexp.HandleCodeSynthesis(squeue, handler, resb, null);
		squeue.add("do {\n\n} while (" + resb.toString() + ");");
		return false;
	}
	
}