package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SequenceManager {
	
	private Sequence exactmatch = null;
	
	private PriorityQueue<Sequence> notexactmatch = new PriorityQueue<Sequence>();
	
	public SequenceManager() {
	}
	
	public SequenceManager(Sequence exactmatch, PriorityQueue<Sequence> notexactmatch)
	{
		this.setExactmatch(exactmatch);
		this.setNotexactmatch(notexactmatch);
	}

	public SequenceManager(ArrayList<SequenceManager> smarray) {
		// TODO
	}

	public SequenceManager HandleANewInSentence(String ons) {
		if (getExactmatch() == null)
		{
			// first line.
			setExactmatch(new Sequence());
			getExactmatch().AddOneSentence(ons);
			return this;
		}
		else
		{
			ArrayList<SequenceManager> smarray = new ArrayList<SequenceManager>();
			int existSize = 1 + getNotexactmatch().size();
			int averagePredict = PredictMetaInfo.PredictMaxSequence / existSize;
			SequenceManager sm = getExactmatch().HandleNewInSentence(ons, averagePredict + 5);
			smarray.add(sm);
			
			Iterator<Sequence> itr = getNotexactmatch().iterator();
			int isize = existSize;
			while (itr.hasNext())
			{
				isize--;
				Sequence seq = itr.next();
				smarray.add(seq.HandleNewInSentence(ons, averagePredict + (int)(5*(isize*1.0/(existSize*1.0)))));
			}
			return new SequenceManager(smarray);
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
	
}