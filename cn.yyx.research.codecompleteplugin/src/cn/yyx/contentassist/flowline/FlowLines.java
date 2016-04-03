package cn.yyx.contentassist.flowline;

import java.util.LinkedList;
import java.util.List;

public class FlowLines<T> {
	
	private FlowLineNode<T> heads = null;
	private List<FlowLineNode<T>> tails = null;
	private List<FlowLineNode<T>> temptails = new LinkedList<FlowLineNode<T>>();
	
	private boolean opflag = false;
	
	public FlowLines() {
	}
	
	public boolean IsEmpty()
	{
		return getHeads() == null;
	}
	
	public void InitialSeed(T t) {
		assert IsEmpty();
		FlowLineNode<T> fln = new FlowLineNode<T>(t, 0);
		setHeads(fln);
		setTails(new LinkedList<FlowLineNode<T>>());
		getTails().add(fln);
	}
	
	private void CheckOperationPermit()
	{
		if (!opflag)
		{
			System.err.println("Flow Line Operation not permitted.");
			System.exit(1);
		}
	}
	
	public void AddToNextLevel(FlowLineNode<T> addnode, FlowLineNode<T> prenode)
	{
		CheckOperationPermit();
		addnode.setPrev(prenode);
		FlowLineNode<T> nextfst = prenode.getNext();
		if (nextfst == null)
		{
			prenode.setNext(addnode);
		}
		else
		{
			FlowLineNode<T> nnt = nextfst.getSilbnext();
			nnt.setSilbprev(addnode);
			addnode.setSilbnext(nnt);
			nextfst.setSilbnext(addnode);
		}
		temptails.add(addnode);
	}
	
	public List<FlowLineNode<T>> GetCurrentTails()
	{
		return getTails();
	}
	
	public void BeginOperation()
	{
		temptails.clear();
		opflag = true;
	}
	
	public void EndOperation()
	{
		opflag = false;
		getTails().clear();
		getTails().addAll(temptails);
	}

	public FlowLineNode<T> getHeads() {
		return heads;
	}

	public void setHeads(FlowLineNode<T> heads) {
		this.heads = heads;
	}

	public List<FlowLineNode<T>> getTails() {
		return tails;
	}

	public void setTails(List<FlowLineNode<T>> tails) {
		this.tails = tails;
	}
	
}