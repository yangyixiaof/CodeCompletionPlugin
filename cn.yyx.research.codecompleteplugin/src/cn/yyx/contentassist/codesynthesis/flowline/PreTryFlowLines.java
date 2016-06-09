package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;

public class PreTryFlowLines<T> extends FlowLines<T> {
	
	// private PreTryFlowLineNode<T> exactmatchtail = null;
	// private FlowLineNode<T> tempexactmatchtail = null;
	
	private List<PreTryFlowLineNode<T>> overtails = null;
	int validovers = 0;
	
	public PreTryFlowLines(List<PreTryFlowLineNode<T>> overtails) {
		this.overtails = overtails;
		this.validovers = overtails.size();
	}
	
	public PreTryFlowLines() {
		overtails = new LinkedList<PreTryFlowLineNode<T>>();
	}
	
	public void AddOverFlowLineNode(PreTryFlowLineNode<T> otail, PreTryFlowLineNode<T> prenode)
	{
		if (otail.getSeqencesimilarity() > PredictMetaInfo.SequenceSimilarThreshold)
		{
			validovers++;
		}
		getOvertails().add(otail);
		otail.setPrev(prenode);
	}
	
	/*public PreTryFlowLineNode<T> getExactmatchtail() {
		return exactmatchtail;
	}

	public void setExactmatchtail(PreTryFlowLineNode<T> exactmatchtail) {
		this.exactmatchtail = exactmatchtail;
	}
	
	public void ClearExactMatch()
	{
		this.exactmatchtail = null;
	}*/
	
	@Override
	public void InitialSeed(T t) {
		assert IsEmpty();
		FlowLineNode<T> fln = new PreTryFlowLineNode<T>(t, 0, 0, null, 0);
		setHeads(fln);
		fln.setLength(1);
		setTails(new LinkedList<FlowLineNode<T>>());
		getTails().add(fln);
		// exactmatchtail = (PreTryFlowLineNode<T>)getHeads();
	}

	public List<PreTryFlowLineNode<T>> getOvertails() {
		return overtails;
	}

	public int GetValidOveredSize() {
		return validovers;
	}
	
	public int GetAllOveredSize() {
		return overtails.size();
	}

	public void TrimOverTails(int needsize) {
		List<PreTryFlowLineNode<T>> finalovertails = new LinkedList<PreTryFlowLineNode<T>>();
		Queue<PreTryFlowLineNode<T>> priorityqueue = new PriorityQueue<PreTryFlowLineNode<T>>();
		Iterator<PreTryFlowLineNode<T>> itr = overtails.iterator();
		while (itr.hasNext())
		{
			PreTryFlowLineNode<T> fln = itr.next();
			priorityqueue.add(fln);
		}
		int msize = Math.min(needsize, overtails.size());
		validovers = 0;
		for (int i=0;i<msize;i++)
		{
			PreTryFlowLineNode<T> pop = priorityqueue.poll();
			if (pop.getSeqencesimilarity() > PredictMetaInfo.SequenceSimilarThreshold)
			{
				if (!WholeKeyPrefixContains(finalovertails, pop))
				{
					validovers++;
					finalovertails.add(pop);
				}
			} else {
				/*if (i == 0)
				{
					finalovertails.add(pop);
				}*/
			}
		}
		overtails = finalovertails;
	}
	
	private boolean WholeKeyPrefixContains(List<PreTryFlowLineNode<T>> fls, PreTryFlowLineNode<T> pop)
	{
		Iterator<PreTryFlowLineNode<T>> fitr = fls.iterator();
		while (fitr.hasNext())
		{
			PreTryFlowLineNode<T> ptfn = fitr.next();
			if (ptfn.getWholekey().startsWith(pop.getWholekey()))
			{
				return true;
			}
		}
		return false;
	}

	public void MoveTempTailLastToFirst() {
		super.MoveTempTailLastToFirst();
	}

	public void MoveTempTailSpecificToFirst(PreTryFlowLineNode<T> nf) {
		Iterator<FlowLineNode<T>> titr = temptails.iterator();
		int idx = -1;
		while (titr.hasNext())
		{
			idx++;
			FlowLineNode<T> fln = titr.next();
			if (fln == nf)
			{
				break;
			}
		}
		temptails.remove(idx);
		temptails.add(0, nf);
	}
	
}