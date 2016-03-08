package cn.yyx.contentassist.codepredict;

import cn.yyx.contentassist.codeutils.CodeSimilarity;
import cn.yyx.contentassist.codeutils.statement;

public class Sentence implements Comparable<Sentence>, CodeSimilarity<Sentence> {
	
	String sentence = "";
	statement smt = null;

	public Sentence(String sentence, statement smt) {
		this.sentence = sentence;
		this.smt = smt;
	}
	
	@Override
	public String toString() {
		return sentence;
	}
	
	@Override
	public int compareTo(Sentence o) {
		return sentence.compareTo(o.sentence);
	}

	@Override
	public boolean CouldThoughtSame(Sentence t) {
		return smt.CouldThoughtSame(t.smt);
	}

	@Override
	public double Similarity(Sentence t) {
		return smt.Similarity(t.smt);
	}
	
}