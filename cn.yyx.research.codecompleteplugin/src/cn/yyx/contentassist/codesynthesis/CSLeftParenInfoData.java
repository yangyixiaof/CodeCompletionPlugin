package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSLeftParenInfoData extends CSFlowLineData{
	
	private int times = -1;
	
	public CSLeftParenInfoData(int times, Integer id, Sentence sete, String data, Integer structsignal, Class<?> dcls,
			boolean hashole, SynthesisHandler handler) {
		super(id, sete, data, structsignal, dcls, hashole, TypeComputationKind.NoOptr, handler);
		this.setTimes(times);
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
}