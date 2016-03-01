package cn.yyx.contentassist.codepredict;

import java.util.PriorityQueue;

public class SequenceManager {
	
	Sequence exactmatch = null;
	
	PriorityQueue<Sequence> notexactmatch = new PriorityQueue<Sequence>();

	public SequenceManager HandleANewInSentence(String ons) {
		SequenceManager result = new SequenceManager();
		
		if (exactmatch == null)
		{
			// first line.
			exactmatch = new Sequence();
			exactmatch.AddOneSentence(ons);
		}
		else
		{
			
		}
		
		return result;
	}
	
}