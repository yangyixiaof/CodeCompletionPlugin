package cn.yyx.contentassist.codesynthesis;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineStamp {

	List<FlowLineNode<CSFlowLineData>> parnodes = new LinkedList<FlowLineNode<CSFlowLineData>>();

	public void AddOneParNode(FlowLineNode<CSFlowLineData> fln) {
		parnodes.add(fln);
	}
	
}