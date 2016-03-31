package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class YJCache<T> {
	
	public static final int MaxSize = 50;
	
	Map<String, T> map = new TreeMap<String, T>();
	Queue<String> fifo = new LinkedList<String>();
	
	public void AddCachePair(String key, T cnt)
	{
		if (map.size() >= MaxSize)
		{
			String del = fifo.poll();
			if (key.equals(del))
			{
				return;
			}
			map.remove(del);
		}
		if (!map.containsKey(key))
		{
			map.put(key, cnt);
			fifo.add(key);
		}
	}
	
	public T GetCachedContent(String key)
	{
		return map.get(key);
	}
	
}