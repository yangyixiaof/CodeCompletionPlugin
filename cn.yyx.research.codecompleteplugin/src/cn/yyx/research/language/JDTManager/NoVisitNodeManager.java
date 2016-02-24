package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

// designed for SimpleName only.
public class NoVisitNodeManager {
	
	Map<Integer, Boolean> mNoVisitNodes = new TreeMap<Integer, Boolean>();
	
	public NoVisitNodeManager() {
		
	}
	
	public void AddNoVisitNode(Integer nodeid)
	{
		mNoVisitNodes.put(nodeid, true);
	}
	
	public void DeleteNoVisit(Integer nodeid) {
		mNoVisitNodes.remove(nodeid);
	}
	
	public boolean NeedVisit(Integer nodeid)
	{
		Boolean novisit = mNoVisitNodes.get(nodeid);
		if (novisit == null)
		{
			return true;
		}
		return false;
	}
	
}