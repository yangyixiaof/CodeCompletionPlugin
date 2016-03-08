package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.Sentence;

public abstract class statement extends OneCode implements CodeSimilarity<statement> {
	
	public abstract void HandleOverSignal(Stack<Integer> cstack);
	public abstract int HandlePredictKind();
	
}
