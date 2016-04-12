package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSPrData extends CSFlowLineData{
	
	public CSPrData(Integer id, Sentence sete, String data, Integer structsignal, Class<?> dcls, boolean hashole,
			TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, structsignal, dcls, hashole, tck, handler);
		this.setShouldskip(true);
	}
	
}
