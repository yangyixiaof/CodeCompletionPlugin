package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SynthesisCodeManager {
	
	private int idx = 0;
	private Map<String, SynthesisCode> syncodes = new TreeMap<String, SynthesisCode>();
	
	public SynthesisCodeManager() {
	}
	
	public int GenerateNextLevelId()
	{
		idx++;
		return idx;
	}
	
	public SynthesisCode GetSynthesisCodeByKey(String id)
	{
		return getSyncodes().get(id);
	}
	
	public void AddSynthesisCode(String id, SynthesisCode sc)
	{
		getSyncodes().put(id, sc);
	}
	
	public Map<String, SynthesisCode> getSyncodes() {
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
			SynthesisCode sc = syncodes.get(key);
			if (sc.isValid())
			{
				String code = sc.getCode();
				result.add(code);
			}
		}
		return result;
	}
	
}