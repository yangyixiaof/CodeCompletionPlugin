package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	Stack<Integer> signals = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last, Stack<Integer> signals) {
		this.last = last;
		this.signals = signals;
	}
	
	public void EnsureAllSignalNull(FlowLineNode<CSFlowLineData> fromwhere) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = fromwhere;
		while (tmp != null)
		{
			tmp.getData().HandleStackSignal(signals);
			tmp = tmp.getPrev();
		}
		if (!signals.isEmpty())
		{
			throw new CodeSynthesisException("Has signal on stack, it should have no signals.");
		}
	}
	
	public FlowLineNode<CSFlowLineData> BackSearchForFirstSpecialClass(Class<?> cls, Stack<Integer> signals) throws CodeSynthesisException {
		int inisize = signals.size();
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			tmpdata.HandleStackSignal(signals);
			if (tmpdata.HasSpecialProperty(cls) && signals.size() <= inisize)
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}
	
	public FlowLineNode<CSFlowLineData> GetSearchedAndHandledBlockStart() {
		return last.getData().getSynthesisCodeManager().getBlockstart();
	}
	
	public void EnsureAllSignalNull() throws CodeSynthesisException
	{
		EnsureAllSignalNull(last);
	}
	
	public void EnsureAllSignalNullFromSecondLast() throws CodeSynthesisException {
		EnsureAllSignalNull(last.getPrev());
	}
	
}