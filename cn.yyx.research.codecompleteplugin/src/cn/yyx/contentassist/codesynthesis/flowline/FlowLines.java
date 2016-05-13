package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.CheckUtil;

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
	
	public void DeleteLastAddedNode() {
		FlowLineNode<T> fln = ((LinkedList<FlowLineNode<T>>)temptails).removeLast();
		CheckUtil.CheckMustNull(fln.getNext(), "the last node has next child?");
		// CheckUtil.CheckMustNull(fln.getSilbnext(), "the last node has silb next child?");
		FlowLineNode<T> spn = fln.getSilbnext();
		if (spn != null)
		{
			FlowLineNode<T> spv = fln.getSilbprev();
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
		temptails.add(addnode);
	}
	
	private void InsertSilb(FlowLineNode<T> afterwhich, FlowLineNode<T> insert)
	{
		FlowLineNode<T> nnt = afterwhich.getSilbnext();
		if (nnt == null)
		{
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