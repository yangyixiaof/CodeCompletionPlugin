package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class SynthesisCodeManager {
	
	public final static FlowLineNode<CSFlowLineData> InternNode = new FlowLineNode<CSFlowLineData>(new CSFlowLineData(0, null, null, null, false, false, TypeComputationKind.NoOptr, TypeComputationKind.NoOptr, null), 0);
	
	private Map<String, FlowLineNode<CSFlowLineData>> syncodes = new TreeMap<String, FlowLineNode<CSFlowLineData>>();
	private FlowLineNode<CSFlowLineData> blockstart = null;
	private String blocktostartid = null;
	
	private int id = 0;
	
	public SynthesisCodeManager() {
	}
	
	public FlowLineNode<CSFlowLineData> GetSynthesisCodeByKey(String id)
	{
		return getSyncodes().get(id);
	}
	
	public void SetBlockStartToInternNode()
	{
		blockstart = InternNode;
	}
	
	public void AddSynthesisCode(String id, FlowLineNode<CSFlowLineData> sc)
	{
		getSyncodes().put(id, sc);
	}
	
	public Map<String, FlowLineNode<CSFlowLineData>> getSyncodes() {
		return syncodes;
	}
	
	public List<String> GetSynthesisedCode()
	{
		List<String> result = new LinkedList<String>();
		Set<String> keys = syncodes.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext())
		{
			String key = itr.next();
			FlowLineNode<CSFlowLineData> sc = syncodes.get(key);
			String code = sc.getData().getData();
			result.add(code);
		}
		return result;
	}

	public FlowLineNode<CSFlowLineData> getBlockstart() {
		return blockstart;
	}

	public void setBlockstart(FlowLineNode<CSFlowLineData> blockstart, String blocktostartid) {
		this.blockstart = blockstart;
		this.setBlocktostartid(blocktostartid);
	}

	public int GenerateNextLevelId() {
		id++;
		return id;
	}

	public String getBlocktostartid() {
		return blocktostartid;
	}

	public void setBlocktostartid(String blocktostartid) {
		this.blocktostartid = blocktostartid;
	}
	
}