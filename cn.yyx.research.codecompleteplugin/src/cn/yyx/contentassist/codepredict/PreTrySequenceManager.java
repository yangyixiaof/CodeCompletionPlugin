package cn.yyx.contentassist.codepredict;

public class PreTrySequenceManager extends SequenceManager{
	
	private PreTrySequence exactmatch = null;

	public PreTrySequence getExactmatch() {
		return exactmatch;
	}

	public void setExactmatch(PreTrySequence exactmatch) {
		this.exactmatch = exactmatch;
	}
	
}
