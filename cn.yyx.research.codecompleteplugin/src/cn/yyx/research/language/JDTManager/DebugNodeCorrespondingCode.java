package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class DebugNodeCorrespondingCode {
	
	static Map<Integer, ASTNode> mIdNodeMap = new TreeMap<Integer, ASTNode>();
	
	public static void AddIdNodePair(ASTNode node)
	{
		mIdNodeMap.put(node.hashCode(), node);
	}
	
	public static ASTNode GetNodeById(Integer nodeHashcode)
	{
		return mIdNodeMap.get(nodeHashcode);
	}
	
}