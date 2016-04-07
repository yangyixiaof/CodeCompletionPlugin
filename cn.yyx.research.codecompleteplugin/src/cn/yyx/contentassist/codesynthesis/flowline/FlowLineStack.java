package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	public void SetLastStructureSignal(int structuresignal)
	{
		last.getData().setStructsignal(structuresignal);
	}

	public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int singal) {
		
		return null;
	}

	public FlowLineNode<CSFlowLineData> GetSearchedAndHandledBlockStart() {
		return last.getData().getSynthesisCodeManager().getBlockstart();
	}
	
	public void EnsureAllSignalNull() throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp.HasPrev())
		{
			if (tmp.getData().getStructsignal() != null)
			{
				throw new CodeSynthesisException("Has signal on stack, it should have no signals.");
			}
			tmp = tmp.getPrev();
		}
	}
	
}