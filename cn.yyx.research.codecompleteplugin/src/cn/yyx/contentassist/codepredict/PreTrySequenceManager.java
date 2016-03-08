package cn.yyx.contentassist.codepredict;

public class PreTrySequenceManager extends SequenceManager{
	
	private PreTrySequence exactmatch = null;
	
	public PreTrySequenceManager() {
	}

	public PreTrySequenceManager(SequenceManager tempsm, String ons) {
		// TODO Auto-generated constructor stub
		
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
