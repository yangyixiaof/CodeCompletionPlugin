package cn.yyx.contentassist.codepredict;

public class Sentence implements Comparable<Sentence> {
	
	String sentence = "";

	public Sentence(String sentence) {
		this.sentence = sentence;
	}
	
	@Override
	public int compareTo(Sentence o) {
		// TODO
		return 0;
	}
	
}
