package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSForIniOverProperty extends CSExtraProperty {
	
	private static CSExtraProperty cd = new CSForIniOverProperty();
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.CommonForExpWaitingOver)
		{
			throw new CodeSynthesisException("for ini over does not have common for prefixed.");
		}
		signals.pop();
		signals.push(DataStructureSignalMetaInfo.CommonForInitWaitingOver);
	}
	
	public static CSExtraProperty GetInstance()
	{
		return cd;
	}

}
