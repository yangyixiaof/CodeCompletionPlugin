package cn.yyx.contentassist.codesynthesis.statementhandler;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSMethodStatementHandler extends CSStatementHandler{
	
	// private int argsize = -1;
	// private String methodname = null;
	// private Map<String, MethodTypeSignature> mtsmap = new TreeMap<String, MethodTypeSignature>();
	
	// this variable is used to speed up the search.
	private FlowLineNode<CSFlowLineData> nextstart = null;
	
	// this points to where the @Em is.
	private FlowLineNode<CSFlowLineData> mostfar = null;
	
	// signals.
	private Stack<Integer> signals = new Stack<Integer>();
	
	public CSMethodStatementHandler(CSStatementHandler csh, boolean hasem) {
		super(csh.getSete(), csh.getProb(), csh.getAoi());
		if (hasem)
		{
			getSignals().push(DataStructureSignalMetaInfo.MethodInvocation);
		}
	}
	
	/*public int getArgsize() {
		return argsize;
	}

	public void setArgsize(int argsize) {
		this.argsize = argsize;
	}*/

	public FlowLineNode<CSFlowLineData> getNextstart() {
		return nextstart;
	}

	public void setNextstart(FlowLineNode<CSFlowLineData> nextstart) {
		this.nextstart = nextstart;
	}

	public FlowLineNode<CSFlowLineData> getMostfar() {
		return mostfar;
	}

	public void setMostfar(FlowLineNode<CSFlowLineData> mostfar) {
		this.mostfar = mostfar;
	}

	public Stack<Integer> getSignals() {
		return signals;
	}

	public void setSignals(Stack<Integer> signals) {
		this.signals = signals;
	}
	
}