package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public abstract class CSExtraProperty {
	
	public abstract void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException;
	
}
