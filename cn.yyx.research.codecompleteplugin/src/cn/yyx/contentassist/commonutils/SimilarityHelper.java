package cn.yyx.contentassist.commonutils;

public class SimilarityHelper {
	
	public static double ComputeTwoIntegerSimilarity(int a, int b)
	{
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		return (min * 1.0)/(max * 1.0);
	}
	
	public static boolean CouldThoughtScopeOffsetSame(int scope1, int scope2, int off1, int off2)
	{
		if ((Math.abs(scope1-scope2) <= 1) && (Math.abs(off1-off2) <= 1))
		{
			return true;
		}
		return false;
	}
	
	public static double ComputeScopeOffsetSimilarity(int scope1, int scope2, int off1, int off2)
	{
		int gap1 = Math.abs(scope1-scope2);
		if (gap1 == 0)
		{
			gap1 = 1;
		}
		int gap2 = Math.abs(off1-off2);
		if (gap2 == 0)
		{
			gap2 = 1;
		}
		return 0.6 + 0.4*(0.5/(gap1*1.0)+0.5/(gap2*1.0));
	}
	
}
