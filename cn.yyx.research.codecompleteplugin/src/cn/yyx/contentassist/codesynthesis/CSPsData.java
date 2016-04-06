package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSPsData extends CSFlowLineData{

	public CSPsData(Integer id, Sentence sete, String data, Integer structsignal, Class<?> dcls, boolean hashole,
			TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, structsignal, dcls, hashole, tck, handler);
		this.setShouldskip(true);
	}

}
