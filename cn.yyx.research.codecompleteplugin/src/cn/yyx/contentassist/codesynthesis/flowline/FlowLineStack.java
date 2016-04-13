package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	/*public void SetLastStructureSignal(int structuresignal)
	{
		last.getData().setStructsignal(structuresignal);
	}

	public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int signal) {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp.HasPrev())
		{
			Integer sig = tmp.getData().getStructsignal();
			if ((sig != null) && (sig == signal))
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}*/
	
	public void EnsureAllSignalNull(FlowLineNode<CSFlowLineData> fromwhere) throws CodeSynthesisException {
		
		FlowLineNode<CSFlowLineData> tmp = fromwhere;
		while (tmp != null)
		{
			if (tmp.getData().getStructsignal() != null)
			{
				throw new CodeSynthesisException("Has signal on stack, it should have no signals.");
			}
			tmp = tmp.getPrev();
		}
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