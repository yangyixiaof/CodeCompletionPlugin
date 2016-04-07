package cn.yyx.contentassist.codesynthesis.flowline;

public class FlowLineStack {
	
	FlowLineNode<CSFlowLineData> last = null;
	
	public FlowLineStack(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	public void SetLastStructureSignal(int structuresignal)
	{
		last.getData().setStructsignal(structuresignal);
	}

	public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int arrayinitialblock) {
		
		return null;
	}

	public FlowLineNode<CSFlowLineData> GetSearchedAndHandledBlockStart() {
		return last.getData().getSynthesisCodeManager().getBlockstart();
	}
	
}