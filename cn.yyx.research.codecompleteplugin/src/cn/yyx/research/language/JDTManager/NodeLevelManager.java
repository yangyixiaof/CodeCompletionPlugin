package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class NodeLevelManager {
	
	int level = 0;
	
	Map<Integer, Integer> nodeLevelMap = new TreeMap<Integer, Integer>();
	
	Map<Integer, Byte> firstNodeAfterDecreasingElement = new TreeMap<Integer, Byte>();
	
	Map<Integer, Byte> lastNodeBeforeIncreaseingElement = new TreeMap<Integer, Byte>();
	
	public NodeLevelManager() {
	}
	
	public void PreVisit(ASTNode node)
	{
		int nid = node.hashCode();
		if (firstNodeAfterDecreasingElement.containsKey(nid))
		{
			level--;
		}
		nodeLevelMap.put(nid, level);
	}
	
	public void Visit(ASTNode node)
	{
		int nid = node.hashCode();
		if (!firstNodeAfterDecreasingElement.containsKey(nid))
		{
			level--;
		}
		nodeLevelMap.put(nid, level);
	}
	
	public void EndVisit(ASTNode node)
	{
		int nid = node.hashCode();
		if (!lastNodeBeforeIncreaseingElement.containsKey(nid))
		{
			level++;
		}
	}
	
	public void PostVisit(ASTNode node)
	{
		int nid = node.hashCode();
		
		firstNodeAfterDecreasingElement.remove(nid);
		
		if (lastNodeBeforeIncreaseingElement.containsKey(nid))
		{
			lastNodeBeforeIncreaseingElement.remove(nid);
			level++;
		}
	}
	
	// The following two functions should be called by ast visit method.
	public void RegistFirstNodeAfterDecreasingElement(ASTNode node) {
		firstNodeAfterDecreasingElement.put(node.hashCode(), (byte)0);
	}
	
	public void RegistLastNodeBeforeIncreaseingElement(ASTNode node) {
		lastNodeBeforeIncreaseingElement.put(node.hashCode(), (byte)0);
	}

	public void DecreaseLevel() {
		level--;
	}

	public void IncreaseLevel() {
		level++;
	}
	
	public int GetNodeLevel(ASTNode node)
	{
		return nodeLevelMap.get(node.hashCode());
	}
	
}