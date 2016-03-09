package cn.yyx.contentassist.commonutils;

public class SimilarityHelper {
	
	public static double ComputeTwoIntegerSimilarity(int a, int b)
	{
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		return (min * 1.0)/(max * 1.0);
	}
	
}
