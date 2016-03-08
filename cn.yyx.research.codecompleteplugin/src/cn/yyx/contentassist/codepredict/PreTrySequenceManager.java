package cn.yyx.contentassist.codepredict;

import java.util.Iterator;

public class PreTrySequenceManager extends SequenceManager{
	
	private PreTrySequence exactmatch = null;
	
	public PreTrySequenceManager() {
	}

	public PreTrySequenceManager(SequenceManager tempsm, Sentence ons, boolean generateFromExactmath) {
		Iterator<Sequence> itr = tempsm.Iterator();
		while (itr.hasNext())
		{
			Sequence seq = itr.next();
			Sentence recent = seq.getLast();
			boolean exactmatch = false;
			// two strings same.
			if (recent.CouldThoughtSame(ons))
			{
				if (generateFromExactmath)
				{
					exactmatch = true;
				}
			}
			PreTrySequence pts = new PreTrySequence(seq, exactmatch);
			AddSequence(pts);
			if (exactmatch)
			{
				this.exactmatch = pts;
			}
		}
	}
	
	@Override
	public void Merge(SequenceManager sm) {
		PreTrySequenceManager pts = (PreTrySequenceManager)sm;
		PreTrySequence em = pts.getExactmatch();
		if (exactmatch.compareTo(em) > 0)
		{
			exactmatch = em;
		}
		super.Merge(sm);
	}

	public PreTrySequence getExactmatch() {
		return exactmatch;
	}

	public void setExactmatch(PreTrySequence exactmatch) {
		this.exactmatch = exactmatch;
	}
	
}
