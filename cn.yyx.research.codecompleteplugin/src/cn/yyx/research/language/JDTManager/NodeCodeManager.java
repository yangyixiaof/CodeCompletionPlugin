package cn.yyx.research.language.JDTManager;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class NodeCodeManager {
	
	Map<Integer, String> mNodeCodeMap = new TreeMap<Integer, String>();
	Map<Integer, Boolean> mNodeHasOccupiedOneLineMap = new TreeMap<Integer, Boolean>();
	Map<Integer, Boolean> mNodeInMutipleLineMap = new TreeMap<Integer, Boolean>();
	Map<Integer, Boolean> mNodeHasContentHolderMap = new TreeMap<Integer, Boolean>();
	// type should be a / c.
	Map<Integer, Character> mNodeTypeMap = new TreeMap<Integer, Character>();
	Map<Integer, Boolean> mNodeHasUsedMap = new TreeMap<Integer, Boolean>();
	Map<Integer, Boolean> mNodeNeedAppendChildPreNodeType = new TreeMap<Integer, Boolean>();
	Map<Integer, Integer> mNodeLinkMap = new TreeMap<Integer, Integer>();
	
	// handle NodeLink. create a new function and while and return the real node. change all other functions the way handle the node.
	
	public NodeCodeManager() {
	}
	
	public void AddNodeNeedAppendChildPreNodeType(ASTNode astnode, boolean needAppend)
	{
		mNodeNeedAppendChildPreNodeType.put(astnode.hashCode(), needAppend);
	}
	
	public boolean GetNodeNeedAppendChildPreNodeType(ASTNode astnode)
	{
		Boolean ifneed = mNodeNeedAppendChildPreNodeType.get(astnode.hashCode());
		if (ifneed == null)
		{
			return false;
		}
		return ifneed;
	}
	
	public void AddASTNodeCode(ASTNode astnode, String generatedCode)
	{
		int astnodehashcode = GetRealNode(astnode);
		mNodeCodeMap.put(astnodehashcode, generatedCode);
	}
	
	public String GetAstNodeCode(ASTNode astnode)
	{
		int astnodehashcode = GetRealNode(astnode);
		return mNodeCodeMap.get(astnodehashcode);
	}
	
	public void AddASTNodeHasOccupiedOneLine(ASTNode astnode, Boolean HasOccupiedOneLine)
	{
		int astnodehashcode = GetRealNode(astnode);
		mNodeHasOccupiedOneLineMap.put(astnodehashcode, HasOccupiedOneLine);
	}
	
	public boolean GetAstNodeHasOccupiedOneLine(ASTNode astnode)
	{
		int astnodehashcode = GetRealNode(astnode);
		Boolean HasOccupiedOneLine = mNodeHasOccupiedOneLineMap.get(astnodehashcode);
		if (HasOccupiedOneLine == null)
		{
			HasOccupiedOneLine = false;
		}
		return HasOccupiedOneLine;
	}
	
	public void AddASTNodeIfHasContentHolder(ASTNode node, Boolean ifHasContentHolder)
	{
		int astnodehashcode = GetRealNode(node);
		mNodeHasContentHolderMap.put(astnodehashcode, ifHasContentHolder);
	}
	
	public boolean GetAstNodeHasContentHolder(ASTNode astnode)
	{
		int astnodehashcode = GetRealNode(astnode);
		Boolean ifHasContentHolder = mNodeHasContentHolderMap.get(astnodehashcode);
		if (ifHasContentHolder == null)
		{
			ifHasContentHolder = false;
		}
		return ifHasContentHolder;
	}
	
	public void AddASTNodeInMultipleLine(ASTNode node, Boolean inMutipleLine)
	{
		int astnodehashcode = GetRealNode(node);
		mNodeInMutipleLineMap.put(astnodehashcode, inMutipleLine);
	}
	
	public boolean GetAstNodeInMultipleLine(ASTNode astnode)
	{
		int astnodecode = astnode.hashCode();
		if (mNodeInMutipleLineMap.get(astnodecode) != null)
		{
			return true;
		}
		int astnodehashcode = GetRealNode(astnode);
		Boolean inMutipleLine = mNodeInMutipleLineMap.get(astnodehashcode);
		if (inMutipleLine == null)
		{
			inMutipleLine = false;
		}
		return inMutipleLine;
	}
	
	public void AddNodeType(ASTNode node, Character type)
	{
		mNodeTypeMap.put(GetRealNode(node), type);
	}
	
	public Character GetNodeType(ASTNode node)
	{
		return mNodeTypeMap.get(GetRealNode(node));
	}
	
	// this should be b type.
	public boolean IsNewStatement(ASTNode node)
	{
		return !mNodeTypeMap.containsKey(GetRealNode(node));
	}
	
	//Map<Integer, Boolean> mNodeHasUsedMap
	public void AddNodeHasUsed(ASTNode node, boolean used)
	{
		mNodeHasUsedMap.put(GetRealNode(node), used);
	}
	
	public boolean GetNodeHasUsed(ASTNode node)
	{
		Boolean hasUsed = mNodeHasUsedMap.get(GetRealNode(node));
		if (hasUsed == null)
		{
			return false;
		}
		return hasUsed;
	}
	
	public void AddNodeLink(ASTNode vnode, ASTNode rnode)
	{
		mNodeLinkMap.put(vnode.hashCode(), rnode.hashCode());
	}
	
	public Integer GetNodeLink(ASTNode vnode)
	{
		return mNodeLinkMap.get(vnode.hashCode());
	}
	
	public Integer GetRealNode(ASTNode vnode)
	{
		Integer vid = vnode.hashCode();
		Integer rid = vid;
		while ((vid = mNodeLinkMap.get(vid)) != null)
		{
			rid = vid;
		}
		return rid;
	}
	
}