package cn.yyx.contentassist.codepredict;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Sequence implements Comparator<Sequence>{
	
	Queue<String> sequence = new LinkedList<String>();
	
	Double prob = (double) 0;

	public void AddOneSentence(String ons) {
		sequence.add(ons);
	}

	@Override
	public int compare(Sequence o1, Sequence o2) {
		return o1.prob.compareTo(o2.prob);
	}

	public SequenceManager HandleNewInSentence(String ons, int neededSize) {
		SequenceManager result = new SequenceManager();
		return result;
	}
	
}