package cn.yyx.contentassist.codesynthesis.data;

import java.util.Map;
import java.util.TreeMap;

public class CSExtraData {
	
	Map<String, Object> extras = null;
	
	public CSExtraData() {
	}
	
	public void AddExtraData(String key, Object data)
	{
		if (extras == null)
		{
			extras = new TreeMap<String, Object>();
		}
		extras.put(key, data);
	}
	
	public Object GetExtraData(String key)
	{
		if (extras == null)
		{
			return null;
		}
		return extras.get(key);
	}
	
}