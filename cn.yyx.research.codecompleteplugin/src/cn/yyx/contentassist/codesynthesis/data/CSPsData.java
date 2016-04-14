package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSPsData extends CSFlowLineData{
	
	public CSPsData(Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		Integer top = signals.peek();
		if (top == null || top != DataStructureSignalMetaInfo.MethodInvocation)
		{
			throw new CodeSynthesisException("When handling pr, the top of stack is not MethodInvocation.");
		}
	}
	
}