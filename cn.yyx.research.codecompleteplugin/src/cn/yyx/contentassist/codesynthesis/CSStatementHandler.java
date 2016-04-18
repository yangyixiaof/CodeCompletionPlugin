package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;

public class CSStatementHandler {
	
	private Sentence sete = null;
	private double prob = 0;
	private ASTOffsetInfo aoi = null;
	
	public CSStatementHandler(Sentence sete, double prob, ASTOffsetInfo aoi) {
		this.setSete(sete);
		this.setProb(prob);
		this.setAoi(aoi);
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

	public ASTOffsetInfo getAoi() {
		return aoi;
	}

	public void setAoi(ASTOffsetInfo aoi) {
		this.aoi = aoi;
	}
	
}