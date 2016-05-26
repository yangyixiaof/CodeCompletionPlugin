package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSEnterParamInfoData extends CSFlowLineData{
	
	private int times = -1;
	
	public CSEnterParamInfoData(int times, Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
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
		super.HandleStackSignal(signals);
		int tttimes = times;
		while (!signals.isEmpty() && tttimes > 0)
		{
			Integer top = signals.peek();
			if (top == null || (top != DataStructureSignalMetaInfo.MethodPs && top != DataStructureSignalMetaInfo.MethodPr))
			{
				throw new CodeSynthesisException("When handling ps, the top of stack is not MethodPs or MethodPr.");
			}
			tttimes--;
			signals.pop();
		}
		// pred check : tttimes > 0
		if (!signals.isEmpty())
		{
			throw new CodeSynthesisException("EnterParam doesn't consumed totally and left can not consume, so it is an error.");
			// signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.MethodEnterParam, tttimes));
		}
	}
	
}