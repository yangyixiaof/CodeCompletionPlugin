package cn.yyx.contentassist.codeutils;

public interface CodeSimilarity<T> {
	
	public boolean CouldThoughtSame(T t);
	public double Similarity(T t);
	
}
