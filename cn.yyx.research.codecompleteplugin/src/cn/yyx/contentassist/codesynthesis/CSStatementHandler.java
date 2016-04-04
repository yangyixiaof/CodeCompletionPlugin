package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;

public class CSStatementHandler {
	
	private Sentence sete = null;
	private double prob = 0;
	
	public CSStatementHandler(Sentence sete, double prob) {
		this.setSete(sete);
		this.setProb(prob);
	}

	public Sentence getSete() {
		return sete;
	}

	public void setSete(Sentence sete) {
		this.sete = sete;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}
	
}