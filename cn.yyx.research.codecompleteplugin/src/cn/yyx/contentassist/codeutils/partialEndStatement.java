package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSParLineNode;

public class partialEndStatement extends statement{

	@Override
	public boolean HandleCodeSynthesis(CSFlowLineQueue squeue, List<CSParLineNode> nextpars) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

}
