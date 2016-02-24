package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class NodeLineManager {
	
	Map<Integer, Integer> mNodeLineMap = new TreeMap<Integer, Integer>();
	
	public NodeLineManager() {
	}
	
	public void AddASTNodeLineInfo(ASTNode astnode, int line)
	{
		int astnodehashcode = astnode.hashCode();
		if (!mNodeLineMap.containsKey(astnodehashcode))
		{
			mNodeLineMap.put(astnodehashcode, line);
		}
	}
	
	public Integer GetAstNodeLineInfo(ASTNode astnode)
	{
		int astnodehashcode = astnode.hashCode();
		return mNodeLineMap.get(astnodehashcode);
	}
	
}