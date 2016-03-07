package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class SequenceManager {
	
	private PriorityQueue<Sequence> sequence = new PriorityQueue<Sequence>();
	// internal use.
	private Map<Integer, Sequence> unique = new TreeMap<Integer, Sequence>();
	private boolean hasRestrained = false;
	
	public SequenceManager() {
	}
	
	// merge must be happened in the same level.
	public void Merge(SequenceManager sm)
	{
		if (hasRestrained)
		{
			System.err.println("Can not merge after restrained");
			System.exit(1);
		}
		Iterator<Sequence> itr = sm.Iterator();
		while (itr.hasNext())
		{
			Sequence seq = itr.next();
			int hash = seq.hashCode();
			if (!unique.containsKey(hash))
			{
				unique.put(hash, seq);
				sequence.add(seq);
			}
		}
	}
	
	public void Restrain(int size)
	{
		hasRestrained = true;
		int nowsize = sequence.size();
		if (nowsize > size)
		{
			int gap = nowsize - size;
			for (int i=0;i<gap;i++)
			{
				sequence.poll();
			}
		}
		unique.clear();
	}
	
	public Iterator<Sequence> Iterator()
	{
		return sequence.iterator();
	}
	
	public void AddSequence(Sequence seq)
	{
		sequence.add(seq);
		unique.put(seq.hashCode(), seq);
	}
	
	public void AddOnePredict(PredictSequence pd, int maxSize)
	{
		sequence.add(pd);
		unique.put(pd.hashCode(), pd);
		if (maxSize > 0)
		{
			int size = sequence.size();
			if (size > maxSize)
			{
				int gap = size - maxSize;
				for (int i=0;i<gap;i++)
				{
					sequence.poll();
				}
			}
		}
	}
	
	public int GetSize()
	{
		return sequence.size();
	}
	
	public boolean IsEmpty()
	{
		return sequence.size() == 0;
	}
	
	/*public SequenceManager(ArrayList<SequenceManager> smarray, Sequence oracle) {
		Sequence match = null;
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
	}*/
	
}