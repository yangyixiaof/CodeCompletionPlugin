package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

public class LabelLineManager {
	
	Map<String, Integer> llmap = new TreeMap<String, Integer>();
	
	public void AddLabelLine(String label, Integer line)
	{
		llmap.put(label, line);
	}
	
	public Integer GetLabelLine(String label)
	{
		return llmap.get(label);
	}
	
}
