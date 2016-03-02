package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class SequenceManager {
	
	private static Sequence exactseq = null;
	
	private Sequence exactmatch = null;
	
	private PriorityQueue<Sequence> notexactmatch = new PriorityQueue<Sequence>();
	
	public SequenceManager() {
	}
	
	public SequenceManager(Sequence exactmatch, PriorityQueue<Sequence> notexactmatch)
	{
		this.setExactmatch(exactmatch);
		this.setNotexactmatch(notexactmatch);
	}
	
	public SequenceManager(ArrayList<SequenceManager> smarray, Sequence oracle) {
		Sequence match = null;
		Map<Integer, Sequence> unique = new TreeMap<Integer, Sequence>();
		PriorityQueue<Sequence> pq = new PriorityQueue<Sequence>();
		Iterator<SequenceManager> itr2 = smarray.iterator();
		while (itr2.hasNext())
		{
			SequenceManager sm = itr2.next();
			Sequence em = sm.getExactmatch();
			if (em != null)
			{
				unique.put(em.hashCode(), em);
				match = em;
			}
			PriorityQueue<Sequence> queue = sm.getNotexactmatch();
			Iterator<Sequence> itr = queue.iterator();
			while (itr.hasNext())
			{
				Sequence seq = itr.next();
				int hash = seq.hashCode();
				if (!unique.containsKey(hash))
				{
					unique.put(hash, seq);
					pq.add(seq);
				}
			}
		}
		if (match == null)
		{
			match = oracle;
		}
		this.exactmatch = match;
		Iterator<Sequence> itr = pq.iterator();
		int size = 0;
		while (itr.hasNext())
		{
			size++;
			Sequence sq = itr.next();
			if (size > PredictMetaInfo.PredictMaxSequence)
			{
				break;
			}
			this.notexactmatch.add(sq);
		}
	}
	
	public SequenceManager HandleANewInSentence(AeroLifeCycle alc, String ons) {
		if (getExactseq() == null)
		{
			setExactseq(new Sequence(true));
		}
		getExactseq().HandleNewInDirectlyToAddOneSentence(ons);
		
		if (getExactmatch() == null)
		{
			// first line.
			setExactmatch(new Sequence(true));
			getExactmatch().HandleNewInDirectlyToAddOneSentence(ons);
			return this;
		}
		else
		{
			ArrayList<SequenceManager> smarray = new ArrayList<SequenceManager>();
			int existSize = 1 + getNotexactmatch().size();
			int averagePredict = PredictMetaInfo.PredictMaxSequence / existSize;
			SequenceManager sm = getExactmatch().HandleNewInSentence(alc, ons, averagePredict + 5);
			smarray.add(sm);
			
			Iterator<Sequence> itr = getNotexactmatch().iterator();
			int isize = existSize;
			while (itr.hasNext())
			{
				isize--;
				Sequence seq = itr.next();
				smarray.add(seq.HandleNewInSentence(alc, ons, averagePredict + (int)(5*(isize*1.0/(existSize*1.0)))));
			}
			return new SequenceManager(smarray, getExactseq());
		}
	}

	public Sequence getExactmatch() {
		return exactmatch;
	}

	public void setExactmatch(Sequence exactmatch) {
		this.exactmatch = exactmatch;
	}

	public PriorityQueue<Sequence> getNotexactmatch() {
		return notexactmatch;
	}

	public void setNotexactmatch(PriorityQueue<Sequence> notexactmatch) {
		this.notexactmatch = notexactmatch;
	}

	public Sequence getExactseq() {
		return exactseq;
	}

	public void setExactseq(Sequence exactseq) {
		SequenceManager.exactseq = exactseq;
	}
	
}