package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;

public class ifStatement extends statement{
	
	referedExpression rexp = null;
	
	public ifStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof ifStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof ifStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((ifStatement) t).rexp));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		int waitkind = cstack.peek();
		if (waitkind == PredictMetaInfo.AllKindWaitingOver)
		{
			cstack.pop();
		}
		cstack.push(PredictMetaInfo.IfOver);
	}
	
}
