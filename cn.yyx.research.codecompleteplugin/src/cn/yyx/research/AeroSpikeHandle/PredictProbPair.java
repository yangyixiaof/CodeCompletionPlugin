package cn.yyx.research.AeroSpikeHandle;

public class PredictProbPair {
	
	private String pred = null;
	private Double prob = (double) 0;
	
	public PredictProbPair(String pred, Double prob)
	{
		this.setPred(pred);
		this.setProb(prob);
	}

	public String getPred() {
		return pred;
	}

	public void setPred(String pred) {
		this.pred = pred;
	}

	public Double getProb() {
		return prob;
	}

	public void setProb(Double prob) {
		this.prob = prob;
	}
	
}