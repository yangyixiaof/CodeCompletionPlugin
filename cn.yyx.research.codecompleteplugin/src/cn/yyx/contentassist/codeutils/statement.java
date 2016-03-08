package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public abstract class statement extends OneCode implements CodeSimilarity<statement> {
	
	public abstract void HandleOverSignal(Stack<Integer> cstack);
	
}
