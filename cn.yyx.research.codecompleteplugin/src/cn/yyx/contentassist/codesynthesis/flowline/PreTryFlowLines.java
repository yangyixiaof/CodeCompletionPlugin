package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;

public class PreTryFlowLines<T> extends FlowLines<T> {
	
	private FlowLineNode<T> exactmatchtail = null;
	// private FlowLineNode<T> tempexactmatchtail = null;
	
	private List<FlowLineNode<T>> overtails = new LinkedList<FlowLineNode<T>>();
	int validovers = 0;
	
	public void AddOverFlowLineNode(PreTryFlowLineNode<T> otail, FlowLineNode<T> prenode)
	{
		if (otail.seqencesimilarity > PredictMetaInfo.SequenceSimilarThreshold)
		{
			validovers++;
		}
		getOvertails().add(otail);
		otail.setPrev(prenode);
	}
	
	public FlowLineNode<T> getExactmatchtail() {
		return exactmatchtail;
	}

	public void setExactmatchtail(FlowLineNode<T> exactmatchtail) {
		this.exactmatchtail = exactmatchtail;
	}
	
	public void ClearExactMatch()
	{
		this.exactmatchtail = null;
	}
	
	/*@Override
	public void EndOperation()
	{
		super.EndOperation();
		exactmatchtail = tempexactmatchtail;
	}*/
	
	@Override
	public void InitialSeed(T t) {
		assert IsEmpty();
		FlowLineNode<T> fln = new PreTryFlowLineNode<T>(t, 0, 0, null);
		setHeads(fln);
		fln.setLength(1);
		setTails(new LinkedList<FlowLineNode<T>>());
		getTails().add(fln);
		exactmatchtail = getHeads();
	}

	public List<FlowLineNode<T>> getOvertails() {
		return overtails;
	}

	public int GetValidOveredSize() {
		return validovers;
	}
	
	public int GetAllOveredSize() {
		return overtails.size();
	}

	public void TrimOverTails(int needsize) {
		List<FlowLineNode<T>> finalovertails = new LinkedList<FlowLineNode<T>>();
		Queue<FlowLineNode<T>> priorityqueue = new PriorityQueue<FlowLineNode<T>>();
		Iterator<FlowLineNode<T>> itr = overtails.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<T> fln = itr.next();
			priorityqueue.add(fln);
		}
		int msize = Math.min(needsize, overtails.size());
		for (int i=0;i<msize;i++)
		{
			finalovertails.add(priorityqueue.poll());
		}
		overtails = finalovertails;
	}
	
}