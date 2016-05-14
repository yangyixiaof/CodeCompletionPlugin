package cn.yyx.contentassist.aerospikehandle;

import cn.yyx.contentassist.codepredict.Sentence;

public class PredictProbPair implements Comparable<PredictProbPair>{
	
	private Sentence pred = null;
	private Double prob = (double) 0;
	
	public PredictProbPair(Sentence pred, Double prob)
	{
		this.setPred(pred);
		this.setProb(prob);
	}

	public Sentence getPred() {
		return pred;
	}

	public void setPred(Sentence pred) {
		this.pred = pred;
	}

	public Double getProb() {
		return prob;
	}

	public void setProb(Double prob) {
		this.prob = prob;
	}

	// order is : bigger former.
	@Override
	public int compareTo(PredictProbPair o) {
		return ((Double)(-prob)).compareTo((-o.prob));
	}
	
}