package cn.yyx.contentassist.codepredict;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Sequence implements Comparator<Sequence>{
	
	private Queue<String> sequence = new LinkedList<String>();
	
	private Double prob = (double) 0;
	
	public void AddOneSentence(String ons) {
		getSequence().add(ons);
	}

	@Override
	public int compare(Sequence o1, Sequence o2) {
		return -o1.getProb().compareTo(o2.getProb());
	}

	public SequenceManager HandleNewInSentence(String ons, int neededSize) {
		SequenceManager result = new SequenceManager();
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		Iterator<String> itr = getSequence().iterator();
		while (itr.hasNext())
		{
			sb.append(" " + itr.next());
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Queue<String> getSequence() {
		return sequence;
	}

	public void setSequence(Queue<String> sequence) {
		this.sequence = sequence;
	}

	public Double getProb() {
		return prob;
	}

	public void setProb(Double prob) {
		this.prob = prob;
	}
	
}