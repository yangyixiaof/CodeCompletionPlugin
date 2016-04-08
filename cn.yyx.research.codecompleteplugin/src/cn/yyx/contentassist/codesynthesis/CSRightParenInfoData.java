package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSRightParenInfoData extends CSFlowLineData{
	
	private int times = 0;
	private FlowLineNode<CSFlowLineData> mostleft = null;
	private int mostleftremain = 0;
	
	public CSRightParenInfoData(int times, Integer id, Sentence sete, String data, Integer structsignal, Class<?> dcls,
			boolean hashole, TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, structsignal, dcls, hashole, tck, handler);
		this.setTimes(times);
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getMostleftremain() {
		return mostleftremain;
	}

	public void setMostleftremain(int mostleftremain) {
		this.mostleftremain = mostleftremain;
	}

	public FlowLineNode<CSFlowLineData> getMostleft() {
		return mostleft;
	}

	public void setMostleft(FlowLineNode<CSFlowLineData> mostleft) {
		this.mostleft = mostleft;
	}
	
}
