package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class VarOrObjReferenceManager {
	
	Map<Integer, Integer> hintReferenceStack = new TreeMap<Integer, Integer>();
	
	public VarOrObjReferenceManager() {
	}
	
	public void AddReferenceUpdateHint(ASTNode node, Integer kind)
	{
		hintReferenceStack.put(node.hashCode(), kind);
	}
	
	public Integer GetReferenceUpdateHint(ASTNode node)
	{
		Integer result = hintReferenceStack.get(node.hashCode());
		if (result == null)
		{
			result = ReferenceHintLibrary.NoHint;
		}
		return result;
	}

	public void DeleteReferenceUpdateHint(ASTNode node) {
		hintReferenceStack.remove(node.hashCode());
	}
	
}