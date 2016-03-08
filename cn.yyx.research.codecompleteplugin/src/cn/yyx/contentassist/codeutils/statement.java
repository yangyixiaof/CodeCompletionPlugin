package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public abstract class statement extends OneCode{
	
	public abstract void HandleOverSignal(Stack<Integer> cstack);
	public abstract int HandlePredictKind();
	
}
