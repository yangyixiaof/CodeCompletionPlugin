package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.data.CSDataMetaInfo;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderExtraInfo;
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
	public static List<FlowLineNode<CSFlowLineData>> ConcateOneFLStamp(String prefix, List<FlowLineNode<CSFlowLineData>> one, String postfix) {
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData dt = fln.getData();
			String cnt = dt.getData();
			cnt = (prefix == null ? "" : prefix) + cnt + (postfix == null ? "" : postfix);
			dt.setData(cnt);
		}
		return one;
	}
	
	public static void AddToEveryParNodeExtraInfo(List<FlowLineNode<CSFlowLineData>> one, String key, Object info)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			fln.getData().getExtraData().AddExtraData(key, info);
		}
	}
	
	public static void AddToEveryRexpParNodeExtraVariableHolderInfo(List<FlowLineNode<CSFlowLineData>> one, String varname)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = one.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData flndata = fln.getData();
			flndata.getExtraData().AddExtraData(CSDataMetaInfo.VariableHolders, new CSVariableHolderExtraInfo(varname, flndata.getClass()));
		}
	}
	
}