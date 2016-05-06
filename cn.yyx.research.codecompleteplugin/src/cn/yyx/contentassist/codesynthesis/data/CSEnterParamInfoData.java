package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSEnterParamInfoData extends CSFlowLineData{
	
	private int times = -1;
	
	public CSEnterParamInfoData(int times, Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
		this.times = times;
	}
	
	/*public CSEnterParamInfoData(int times, SynthesisHandler handler) {
		super(handler);
		this.times = times;
	}*/

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException {
		int tttimes = times;
		while (!signals.isEmpty() && tttimes > 0)
		{
			Integer top = signals.peek();
			if (top == null || top != DataStructureSignalMetaInfo.MethodPs || top != DataStructureSignalMetaInfo.MethodPr || top != DataStructureSignalMetaInfo.MethodEnterParam)
			{
				throw new CodeSynthesisException("When handling ps, the top of stack is not MethodPs or MethodPr.");
			}
			tttimes--;
			signals.pop();
		}
		if (signals.isEmpty())
		{
			signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.MethodEnterParam, tttimes));
		}
	}
	
}