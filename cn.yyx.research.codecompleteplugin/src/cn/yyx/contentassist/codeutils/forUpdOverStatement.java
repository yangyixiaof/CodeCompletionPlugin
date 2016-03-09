package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;

public class forUpdOverStatement extends statement{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forUpdOverStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forUpdOverStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		int signal = cstack.peek();
		if (signal != PredictMetaInfo.CommonForKindWaitingOver)
		{
			System.err.println("What the fuck, pre is not for?");
			new Exception().printStackTrace();
			System.exit(1);
		}
	}
	
}