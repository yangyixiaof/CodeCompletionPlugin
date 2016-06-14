package cn.yyx.contentassist.commonutils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
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
		if (Math.abs(d1-d2) < PredictMetaInfo.DoubleSameGap)
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
		double tpsim = ComputeListsOfTypeSimilarity(tps1, tps2);
		if (tpsim > PredictMetaInfo.SequenceSimilarThreshold)
		{
			return true;
		}
		return false;
	}
	
	public static double ComputeListsOfTypeSimilarity(List<type> tps1, List<type> tps2)
	{
		return LCSComparison.LCSSimilarityListType(tps1, tps2);
	}
	
	public static double ComputeTwoStringSimilarity(String one, String two) {
		String[] ones = AllToLowerCase(StringUtils.splitByCharacterTypeCamelCase(one));
		String[] twos = AllToLowerCase(StringUtils.splitByCharacterTypeCamelCase(two));
		return LCSComparison.LCSSimilarityString(ones, twos);
	}
	
	private static String[] AllToLowerCase(String[] strs)
	{
		int len = strs.length;
		for (int i=0;i<len;i++)
		{
			strs[i] = strs[i].toLowerCase();
		}
		return strs;
	}
	
}