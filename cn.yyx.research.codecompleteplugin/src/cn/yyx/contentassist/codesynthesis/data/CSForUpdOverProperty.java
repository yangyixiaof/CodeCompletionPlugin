package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSForUpdOverProperty extends CSExtraProperty {
	
	private static CSExtraProperty cd = new CSForUpdOverProperty();
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		signals.push(DataStructureSignalMetaInfo.CommonForUpdWaitingOver);
	}

	public static CSExtraProperty GetInstance()
	{
		return cd;
	}
	
}
