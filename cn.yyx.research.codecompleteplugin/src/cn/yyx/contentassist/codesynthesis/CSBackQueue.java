package cn.yyx.contentassist.codesynthesis;

public class CSBackQueue {
	
	CSParLineNode last = null;
	
	public CSBackQueue(CSParLineNode last) {
		this.last = last;
	}

	public CSParLineNode GetLast() {
		return last;
	}
	
	// merge code ... ... etc.
	
}