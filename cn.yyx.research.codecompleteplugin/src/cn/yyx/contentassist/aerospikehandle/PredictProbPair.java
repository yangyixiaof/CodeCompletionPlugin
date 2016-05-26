package cn.yyx.contentassist.aerospikehandle;

import cn.yyx.contentassist.codepredict.Sentence;

public class PredictProbPair implements Comparable<PredictProbPair>{
	
	private Sentence pred = null;
	private Double prob = (double) 0;
	private int keylen = 0;
	
	public PredictProbPair(Sentence pred, Double prob, int keylen)
	{
		this.setPred(pred);
		this.setProb(prob);
		this.setKeylen(keylen);
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
		if (keylen == o.keylen)
		{
			return ((Double)(-prob)).compareTo((Double)(-o.prob));
		}
		return ((Integer)(-keylen)).compareTo((Integer)(-o.keylen));
	}
	
	@Override
	public String toString() {
		return "predict sentence:" + pred + ";keylen:" + keylen + ";prob:" + prob;
	}

	public int getKeylen() {
		return keylen;
	}

	public void setKeylen(int keylen) {
		this.keylen = keylen;
	}
	
}