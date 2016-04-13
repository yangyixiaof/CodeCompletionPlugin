package cn.yyx.contentassist.codesynthesis.data;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSLeftParenInfoData extends CSFlowLineData{
	
	private int times = -1;
	
	// only temp used.
	private Map<Long, Integer> tempusedtimes = new TreeMap<Long, Integer>();
	
	public CSLeftParenInfoData(int times, Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
		this.setTimes(times);
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	@Override
	public String getData() {
		Thread current = Thread.currentThread();
		int usedtimes = tempusedtimes.get(current.getId());
		return CodeSynthesisHelper.GenerateCopiedContent(usedtimes, "(");
	}
	
	public void AddThreadLeftUsedTimesInfo(Long threadid, int usedtimes)
	{
		tempusedtimes.put(threadid, usedtimes);
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.ParentheseBlock, times));
	}
	
}