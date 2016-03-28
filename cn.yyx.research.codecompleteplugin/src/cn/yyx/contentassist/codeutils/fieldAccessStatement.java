package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		fa.HandleCodeSynthesis(squeue, expected, handler, fcs, null);
		squeue.add(fcs);
		return false;
	}
	
}