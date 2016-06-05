package cn.yyx.contentassist.codepredict;

import cn.yyx.contentassist.codeutils.CodeSimilarity;
import cn.yyx.contentassist.codeutils.statement;

public class Sentence implements Comparable<Sentence>, CodeSimilarity<Sentence>, Cloneable {
	
	private String sentence = "";
	private statement smt = null;

	public Sentence(String sentence, statement smt) {
		this.setSentence(sentence);
		this.setSmt(smt);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Sentence(sentence, smt);
	}
	
	@Override
	public String toString() {
		return getSentence();
	}
	
	@Override
	public int compareTo(Sentence o) {
		return getSentence().compareTo(o.getSentence());
	}

	@Override
	public boolean CouldThoughtSame(Sentence t) {
		return getSmt().CouldThoughtSame(t.getSmt());
	}

	@Override
	public double Similarity(Sentence t) {
		return getSmt().Similarity(t.getSmt());
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public statement getSmt() {
		return smt;
	}

	public void setSmt(statement smt) {
		this.smt = smt;
	}
	
}