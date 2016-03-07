package cn.yyx.contentassist.codepredict;

public class PreTrySequence extends Sequence{
	
	private boolean exactmatch = false;
	
	public PreTrySequence(boolean exactmatch) {
		this.exactmatch = exactmatch;
	}
	
	public boolean isExactmatch() {
		return exactmatch;
	}
	
	public void setExactmatch(boolean exactmatch) {
		this.exactmatch = exactmatch;
	}
	
}
