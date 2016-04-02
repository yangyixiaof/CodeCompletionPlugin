package cn.yyx.contentassist.codesynthesis;

import java.util.Map;
import java.util.TreeMap;

public class CSData {
	
	private Map<String, Class<?>> datas = new TreeMap<String, Class<?>>();
	private String prefix = null;
	private String postfix = null;
	
	public CSData() {
	}
	
	public void AddDataCLassPair(String data, Class<?> cls)
	{
		getDatas().put(data, cls);
	}
	
	public boolean IsEmpty()
	{
		return datas.size() == 0;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public Map<String, Class<?>> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Class<?>> datas) {
		this.datas = datas;
	}
	
}