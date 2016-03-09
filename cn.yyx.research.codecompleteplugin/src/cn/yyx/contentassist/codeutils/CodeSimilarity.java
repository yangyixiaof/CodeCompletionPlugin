package cn.yyx.contentassist.codeutils;

public interface CodeSimilarity<T> {
	
	public boolean CouldThoughtSame(T t);
	/**
	 * 
	 * @param t
	 * @return between 0-1
	 */
	public double Similarity(T t);
	
}
