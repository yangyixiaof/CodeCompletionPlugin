package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;

public class CSArrayAccessEndProperty extends CSExtraProperty {
	
	private int times = -1;
	
	public CSArrayAccessEndProperty(int times, CSExtraProperty csepnext) {
		super(csepnext);
		this.times = times;
	}
	
	/*public CSArrayAccessEndProperty(int times, Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		this.setTimes(times);
	}*/
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException{
		ComplicatedSignal cs = new ComplicatedSignal(DataStructureSignalMetaInfo.ArrayAccessBlcok, times);
		signals.push(cs.GetSignal());
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
}