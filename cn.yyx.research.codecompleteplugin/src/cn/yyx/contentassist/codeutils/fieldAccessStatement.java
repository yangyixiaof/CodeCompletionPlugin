package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class fieldAccessStatement extends expressionStatement{
	
	fieldAccess fa = null;
	
	public fieldAccessStatement(fieldAccess fa) {
		this.fa = fa;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldAccessStatement)
		{
			if (fa.CouldThoughtSame(((fieldAccessStatement) t).fa))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldAccessStatement)
		{
			return 0.3 + 0.7*(fa.Similarity(((fieldAccessStatement) t).fa));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder fasb = new StringBuilder("");
		fa.HandleCodeSynthesis(squeue, handler, fasb, null);
		squeue.add(fasb.toString());
		return false;
	}
	
}