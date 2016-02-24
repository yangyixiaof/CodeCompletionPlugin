package cn.yyx.research.language.JDTManager;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jdt.core.dom.ASTNode;

public class HoleManager {
	
	Queue<ASTNode> holeQueue = new LinkedList<ASTNode>();
	
	public HoleManager() {
	}
	
	public ASTNode PollOfQueue()
	{
		return holeQueue.poll();
	}
	
	public void PushOfQueue(ASTNode holenode)
	{
		holeQueue.offer(holenode);
	}
	
}