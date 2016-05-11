package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.lang3.StringUtils;

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
		Iterator<type> itr1 = tps1.iterator();
		Iterator<type> itr2 = tps2.iterator();
		double tmax = 0;
		while (itr1.hasNext())
		{
			type t1 = itr1.next();
			while (itr2.hasNext())
			{
				type t2 = itr2.next();
				double tsmil = t1.Similarity(t2);
				if (tmax < tsmil)
				{
					tmax = tsmil;
				}
			}
		}
		return tmax;
	}
	
	public static double ComputeTwoStringSimilarity(String one, String two) {
		String[] ones = StringUtils.splitByCharacterTypeCamelCase("one");
		String[] twos = StringUtils.splitByCharacterTypeCamelCase("two");
		int onelen = ones.length;
		int twolen = twos.length;
		PriorityQueue<Double> pq = new PriorityQueue<Double>();
		for (int i=0;i<onelen;i++)
		{
			double maxsim = 0;
			for (int j=0;j<twolen;j++)
			{
				double sim = ComputeTwoStringDirectSimilarity(ones[i], twos[j]);
				if (maxsim < sim)
				{
					maxsim = sim;
				}
			}
			pq.add(-maxsim);
		}
		double allprob = 0;
		int countsize = (int)(onelen*1.0/3.0*2.0);
		if (countsize == 0)
		{
			countsize = 1;
		}
		for (int i=0;i<countsize;i++)
		{
			allprob += -pq.poll();
		}
		return allprob/countsize;
	}
	
	private static double ComputeTwoStringDirectSimilarity(String one, String two)
	{
		char[] a = one.toLowerCase().toCharArray();
		char[] b = two.toLowerCase().toCharArray();
		int len_a = a.length;
		int len_b = b.length;
		int[][] temp = new int[len_a + 1][len_b + 1];
		int distance = compute_distance(a, b, temp);
		int min = Math.min(len_a, len_b);
		double dissimilar = (distance*1.0)/(min*1.0);
		return 1-dissimilar;
	}

	private static int compute_distance(char[] strA, char[] strB, int[][] temp) {
		int i, j;
		int len_a = strA.length;
		int len_b = strB.length;

		for (i = 1; i <= len_a; i++) {
			temp[i][0] = i;
		}

		for (j = 1; j <= len_b; j++) {
			temp[0][j] = j;
		}

		temp[0][0] = 0;

		for (i = 1; i <= len_a; i++) {
			for (j = 1; j <= len_b; j++) {
				if (strA[i - 1] == strB[j - 1]) {
					temp[i][j] = temp[i - 1][j - 1];
				} else {
					temp[i][j] = min(temp[i - 1][j], temp[i][j - 1], temp[i - 1][j - 1]) + 1;
				}
			}
		}
		return temp[len_a][len_b];
	}

	private static int min(int a, int b, int c) {
		if (a < b) {
			if (a < c)
				return a;
			else
				return c;
		} else {
			if (b < c)
				return b;
			else
				return c;
		}
	}
	
}