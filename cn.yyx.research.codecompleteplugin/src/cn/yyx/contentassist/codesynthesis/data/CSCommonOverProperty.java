package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSCommonOverProperty extends CSExtraProperty {

	public CSCommonOverProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}

	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		// do nothing.
	}
	
}