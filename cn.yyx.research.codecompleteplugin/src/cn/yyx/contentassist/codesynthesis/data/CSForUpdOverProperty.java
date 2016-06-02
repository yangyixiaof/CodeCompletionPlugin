package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSForUpdOverProperty extends CSExtraProperty {
	
	public CSForUpdOverProperty(CSExtraProperty csepnext) {
		super(csepnext);
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		signals.push(DataStructureSignalMetaInfo.CommonForUpdWaitingOver);
	}
	
}