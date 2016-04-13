package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSForData extends CSFlowLineData{

	public CSForData(Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre, boolean hashole,
			TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		Integer sl = signals.peek();
		if (sl == null || sl != DataStructureSignalMetaInfo.CommonForInitWaitingOver)
		{
			throw new CodeSynthesisException("for ini over does not have common for prefixed.");
		}
		signals.pop();
	}

}
