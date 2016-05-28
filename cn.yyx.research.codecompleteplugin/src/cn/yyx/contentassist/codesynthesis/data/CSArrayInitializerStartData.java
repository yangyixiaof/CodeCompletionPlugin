package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSArrayInitializerStartData extends CSFlowLineData{

	public CSArrayInitializerStartData(Integer id, Sentence sete, String data, CCType dcls, boolean haspre, boolean hashole,
			TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, tck, handler);
	}
	
}