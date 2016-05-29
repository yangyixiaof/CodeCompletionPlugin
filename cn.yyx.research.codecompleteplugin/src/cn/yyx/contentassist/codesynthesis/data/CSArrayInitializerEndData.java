package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSArrayInitializerEndData extends CSFlowLineData{

	public CSArrayInitializerEndData(Integer id, Sentence sete, String data, CCType dcls, TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, dcls, tck, handler);
	}
	
}
