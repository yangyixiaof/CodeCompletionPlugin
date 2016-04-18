package cn.yyx.contentassist.codesynthesis;

import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;

public class CSMethodStatementHandler extends CSStatementHandler{
	
	private int argsize = -1;
	private String methodname = null;
	private Map<String, MethodTypeSignature> mtsmap = new TreeMap<String, MethodTypeSignature>();
	
	// this variable is used to speed up the search.
	private FlowLineNode<CSFlowLineData> nextstart = null;
	
	// this points to where the @Em is.
	private FlowLineNode<CSFlowLineData> mostfar = null;
	
	public CSMethodStatementHandler(String methodname, CSStatementHandler csh) {
		super(csh.getSete(), csh.getProb(), csh.getAoi());
		this.setMethodname(methodname);
	}
	
	public String getMethodname() {
		return methodname;
	}
	
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	
	public MethodTypeSignature GetMethodTypeSigById(String id)
	{
		return mtsmap.get(id);
	}
	
	public void AddMethodTypeSigById(String id, MethodTypeSignature mts)
	{
		mtsmap.put(id, mts);
	}

	public int getArgsize() {
		return argsize;
	}

	public void setArgsize(int argsize) {
		this.argsize = argsize;
	}

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
	
}