package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public interface CSDataStructure {
	
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException;
	
}
