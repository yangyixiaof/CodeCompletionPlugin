package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.yyx.contentassist.codepredict.LCSComparison;
import cn.yyx.contentassist.codeutils.type;

public class SimilarityHelper {

	public static double ComputeTwoIntegerSimilarity(int a, int b) {
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		return (min * 1.0) / (max * 1.0);
	}
	
	public static boolean CouldThoughtTwoDoubleSame(double d1, double d2)
	{
		if (Math.abs(d1-d2) < 0.05)
		{
			return true;
		}
		return false;
	}

	public static boolean CouldThoughtScopeOffsetSame(int scope1, int scope2, int off1, int off2) {
		if ((Math.abs(scope1 - scope2) <= 1) && (Math.abs(off1 - off2) <= 1)) {
			return true;
		}
		return false;
	}

	public static double ComputeScopeOffsetSimilarity(int scope1, int scope2, int off1, int off2) {
		int gap1 = Math.abs(scope1 - scope2);
		if (gap1 == 0) {
			gap1 = 1;
		}
		int gap2 = Math.abs(off1 - off2);
		if (gap2 == 0) {
			gap2 = 1;
		}
		return 0.6 + 0.4 * (0.5 / (gap1 * 1.0) + 0.5 / (gap2 * 1.0));
	}
	
	public static boolean CouldThoughtListsOfTypeSame(List<type> tps1, List<type> tps2)
	{
		Iterator<type> itr1 = tps1.iterator();
		Iterator<type> itr2 = tps2.iterator();
		while (itr1.hasNext())
		{
			type t1 = itr1.next();
			while (itr2.hasNext())
			{
				type t2 = itr2.next();
				if (t1.CouldThoughtSame(t2))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static double ComputeListsOfTypeSimilarity(List<type> tps1, List<type> tps2)
	{
		return LCSComparison.LCSSimilarityListType(tps1, tps2);
	}
	
	public static double ComputeTwoStringSimilarity(String one, String two) {
		String[] ones = StringUtils.splitByCharacterTypeCamelCase(one);
		String[] twos = StringUtils.splitByCharacterTypeCamelCase(two);
		return LCSComparison.LCSSimilarityString(ones, twos);
	}
	
}