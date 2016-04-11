package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;

import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public class CSFlowLineHelper {
	
	/**
	 * Warning: this function only operate raw data string, please notice the
	 * connect information.
	 * 
	 * @param prefix
	 * @param one
	 * @param postfix
	 */
	public static CSFlowLineStamp ConcateOneFLStamp(String prefix, CSFlowLineStamp one, String postfix) {
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.Iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData dt = fln.getData();
			String cnt = dt.getData();
			cnt = (prefix == null ? "" : prefix) + cnt + (postfix == null ? "" : postfix);
			dt.setData(cnt);
		}
		return one;
	}

}