package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSArrayAccessEndData extends CSFlowLineData{
	
	private int times = -1;
	
	public CSArrayAccessEndData(int times, CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.isHaspre(), cd.isHashole(), cd.getPretck(),
				cd.getPosttck(), cd.getHandler());
		this.setTimes(times);
		this.setCsep(cd.getCsep());
		this.setScm(cd.getSynthesisCodeManager());
	}
	
	public CSArrayAccessEndData(int times, Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
		this.setTimes(times);
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		super.HandleStackSignal(signals);
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