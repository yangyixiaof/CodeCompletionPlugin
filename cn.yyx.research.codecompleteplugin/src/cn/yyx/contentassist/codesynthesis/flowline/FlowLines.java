package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.CheckUtil;

public class FlowLines<T> {
	
	protected FlowLineNode<T> heads = null;
	protected List<FlowLineNode<T>> tails = new LinkedList<FlowLineNode<T>>();
	protected List<FlowLineNode<T>> temptails = new LinkedList<FlowLineNode<T>>();
	
	protected boolean opflag = false;
	
	public FlowLines() {
	}
	
	public boolean IsEmpty()
	{
		return getHeads() == null;
	}
	
	public FlowLineNode<T> InitialSeed(T t) {
		assert IsEmpty();
		FlowLineNode<T> fln = new FlowLineNode<T>(t, 0);
		setHeads(fln);
		getTails().add(fln);
		return fln;
	}
	
	protected void CheckOperationPermit()
	{
		if (!opflag)
		{
			System.err.println("Flow Line Operation not permitted.");
			System.exit(1);
		}
	}
	
	public void DeleteLastAddedNode() {
		FlowLineNode<T> fln = ((LinkedList<FlowLineNode<T>>)temptails).removeLast();
		CheckUtil.CheckMustNull(fln.getNext(), "the last node has next child?");
		// CheckUtil.CheckMustNull(fln.getSilbnext(), "the last node has silb next child?");
		FlowLineNode<T> spn = fln.getSilbnext();
		if (spn != null)
		{
			FlowLineNode<T> spv = fln.getSilbprev();
			// this is right, because this deletes the last added node, so delete the first element which has silb next will never happen.
			CheckUtil.CheckNotNull(spv, "silb prev must not null but this is null.");
			spn.setSilbprev(spv);
			spv.setSilbnext(spn);
		}
		else
		{
			FlowLineNode<T> spv = fln.getSilbprev();
			if (spv == null)
			{
				CheckUtil.CheckRefSame(fln.getPrev().getNext(), fln, "silb next and silb prev are all null and this is not the first one?");
				fln.getPrev().setNext(null);
			}
			else
			{
				spv.setSilbnext(null);
			}
		}
	}
	
	public void MoveTempTailLastToFirst() {
		FlowLineNode<T> fln = ((LinkedList<FlowLineNode<T>>)temptails).removeLast();
		temptails.add(0, fln);
	}
	
	public void AddToNextLevel(FlowLineNode<T> addnode, FlowLineNode<T> prenode)
	{
		CheckOperationPermit();
		if (prenode == null)
		{
			// operate heads.
			addnode.setLength(1);
			if (heads == null)
			{
				heads = addnode;
			}
			else
			{
				InsertSilb(heads, addnode);
			}
		}
		else
		{
			addnode.setLength(prenode.getLength()+1);
			addnode.setPrev(prenode);
			FlowLineNode<T> nextfst = prenode.getNext();
			if (nextfst == null)
			{
				prenode.setNext(addnode);
			}
			else
			{
				InsertSilb(nextfst, addnode);
			}
		}
		TempTailOperation(addnode);
	}
	
	protected void TempTailOperation(FlowLineNode<T> addnode)
	{
		temptails.add(addnode);
	}
	
	private void InsertSilb(FlowLineNode<T> afterwhich, FlowLineNode<T> insert)
	{
		FlowLineNode<T> nnt = afterwhich.getSilbnext();
		if (nnt == null)
		{
			insert.setSilbprev(afterwhich);
			afterwhich.setSilbnext(insert);
		}
		else
		{
			nnt.setSilbprev(insert);
			insert.setSilbnext(nnt);
			insert.setSilbprev(afterwhich);
			afterwhich.setSilbnext(insert);
		}
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