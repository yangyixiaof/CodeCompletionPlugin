package cn.yyx.contentassist.commonutils;

public class SimilarityHelper {

	public static double ComputeTwoIntegerSimilarity(int a, int b) {
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		return (min * 1.0) / (max * 1.0);
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

	public static double ComputeTwoStringSimilarity(String one, String two) {
		char[] a = one.toCharArray();
		char[] b = two.toCharArray();
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
