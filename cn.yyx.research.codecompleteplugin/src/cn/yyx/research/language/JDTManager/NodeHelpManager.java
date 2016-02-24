package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import cn.yyx.research.language.simplified.JDTManager.ConflictASTNodeHashCodeError;

public class NodeHelpManager<T> {
	
	Map<Integer, T> helps = new TreeMap<Integer, T>();
	
	public void AddNodeHelp(Integer key, T value)
	{
		if (helps.get(key) != null)
		{
			throw new ConflictASTNodeHashCodeError("Node Help ast node hashcode conflict error.");
		}
		helps.put(key, value);
	}
	
	public T DeleteNodeHelp(Integer key)
	{
		return helps.remove(key);
	}
	
	public T GetNodeHelp(Integer key)
	{
		return helps.get(key);
	}
	
}
