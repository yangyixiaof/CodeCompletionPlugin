package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;

public class CSRightParenInfoProperty extends CSExtraProperty {
	
	private int times = 0;
	
	public CSRightParenInfoProperty(int times) {
		this.setTimes(times);
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.ParentheseBlock, times));
	}
	
}