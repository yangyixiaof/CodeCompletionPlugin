package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSEnterParamInfoProperty extends CSExtraProperty{
	
	private int times = -1;
	
	public CSEnterParamInfoProperty(int times, CSExtraProperty csepnext) {
		super(csepnext);
		this.times = times;
	}
	
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		int tttimes = times;
		while (!signals.isEmpty() && tttimes > 0)
		{
			Integer top = signals.peek();
			if (top == null || (top != DataStructureSignalMetaInfo.MethodPs && top != DataStructureSignalMetaInfo.MethodPr))
			{
				throw new CodeSynthesisException("When handling ps, the top of stack is not MethodPs or MethodPr.");
			}
			tttimes--;
			signals.pop();
		}
		// pred check : tttimes > 0
		if (!signals.isEmpty())
		{
			int sig = signals.peek();
			if (sig != DataStructureSignalMetaInfo.MethodPs && sig != DataStructureSignalMetaInfo.MethodPr && sig != DataStructureSignalMetaInfo.MethodInvocation) {
			} else {
				throw new CodeSynthesisException("EnterParam doesn't consumed totally and left can not consume, so it is an error.");
			}
		}
	}
	
}