package cn.yyx.research.language.JDTManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class VDataPool {
	
	Map<Integer, JCScope> scopes = new TreeMap<Integer, JCScope>();
	
	public void AScopeCreated(OneScope scope)
	{
		int id = scope.getID();
		JCScope jscope = new JCScope(id, scope.getLevel());
		scopes.put(id, jscope);
	}
	
	public void AScopeDestroyed(OneScope scope)
	{
		int id = scope.getID();
		scopes.remove(id);
	}
	
	public void NewlyAssignedData(OneScope scope, String data, String type)
	{
		JCScope dataScope = GetDataScope(scope);
		dataScope.PushNewlyAssignedData(data, type);
	}
	
	public Integer GetExactDataOffsetInDataOwnScope(OneScope scope, String data, String type)
	{
		JCScope dataScope = GetDataScope(scope);
		return dataScope.GetExactOffset(data, type);
	}
	
	public void ResetClassScope(OneScope scope, LinkedList<String> orderedData, LinkedList<String> orderedType) {
		JCScope dataScope = GetDataScope(scope);
		dataScope.ResetScope(orderedData, orderedType);
	}
	
	private JCScope GetDataScope(OneScope scope)
	{
		int id = scope.getID();
		Integer scopeid = id;
		if (!scopes.containsKey(scopeid))
		{
			System.err.println("Error! no handled scope? The system will exit." + " scope id:" + scopeid + " scope content:" + DebugNodeCorrespondingCode.GetNodeById(scopeid));
			new Exception().printStackTrace();
			System.exit(1);
		}
		return scopes.get(scopeid);
	}
	
	public void ClearAll()
	{
		Set<Integer> keys = scopes.keySet();
		Iterator<Integer> itr = keys.iterator();
		while (itr.hasNext())
		{
			Integer key = itr.next();
			JCScope jcs = scopes.get(key);
			jcs.ClearAll();
		}
		/*if (allitrs > 1)
		{
			System.err.println("VDataPool's ClearAll method must be called in first level.");
			new Exception().printStackTrace();
			System.exit(1);
		}*/
	}
	
}