package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codeutils.OneCode;
import cn.yyx.contentassist.codeutils.argType;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.codeutils.typeArgument;

public class LCSComparison {
	
	public static double LCSSimilarityOneCode(List<OneCode> x, List<OneCode> y) {
		int m = x.size();
		int n = y.size();
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		Iterator<OneCode> iitr = x.iterator();
		int iidx = 0;
		while (iitr.hasNext())
		{
			iidx++;
			OneCode ismt = iitr.next();
			Iterator<OneCode> jitr = y.iterator();
			int jidx = 0;
			while (jitr.hasNext())
			{
				jidx++;
				OneCode jsmt = jitr.next();
				double sim = ismt.Similarity(jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
	}
	
	public static double LCSSimilarity(List<statement> x, List<statement> y) {
		int m = x.size();
		int n = y.size();
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		Iterator<statement> iitr = x.iterator();
		int iidx = 0;
		while (iitr.hasNext())
		{
			iidx++;
			statement ismt = iitr.next();
			Iterator<statement> jitr = y.iterator();
			int jidx = 0;
			while (jitr.hasNext())
			{
				jidx++;
				statement jsmt = jitr.next();
				double sim = ismt.Similarity(jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
	}
	
	public static double LCSSimilarityListType(List<type> x, List<type> y) {
		int m = x.size();
		int n = y.size();
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		Iterator<type> iitr = x.iterator();
		int iidx = 0;
		while (iitr.hasNext())
		{
			iidx++;
			type ismt = iitr.next();
			Iterator<type> jitr = y.iterator();
			int jidx = 0;
			while (jitr.hasNext())
			{
				jidx++;
				type jsmt = jitr.next();
				double sim = ismt.Similarity(jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
	}
	
	public static double LCSSimilarityArgType(List<argType> x, List<argType> y) {
		int m = x.size();
		int n = y.size();
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		Iterator<argType> iitr = x.iterator();
		int iidx = 0;
		while (iitr.hasNext())
		{
			iidx++;
			argType ismt = iitr.next();
			Iterator<argType> jitr = y.iterator();
			int jidx = 0;
			while (jitr.hasNext())
			{
				jidx++;
				argType jsmt = jitr.next();
				double sim = ismt.Similarity(jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
	}
	
	public static double LCSSimilarityTypeArgument(List<typeArgument> x, List<typeArgument> y) {
		int m = x.size();
		int n = y.size();
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		Iterator<typeArgument> iitr = x.iterator();
		int iidx = 0;
		while (iitr.hasNext())
		{
			iidx++;
			typeArgument ismt = iitr.next();
			Iterator<typeArgument> jitr = y.iterator();
			int jidx = 0;
			while (jitr.hasNext())
			{
				jidx++;
				typeArgument jsmt = jitr.next();
				double sim = ismt.Similarity(jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
	}
	
	public static double LCSSimilarityString(String[] x, String[] y) {
		int m = x.length;
		int n = y.length;
		double[][] c = new double[m+1][n+1];
		for (int i = 1; i <= m; i++)
		{
			c[i][0] = 0;
		}
		for (int j = 0; j <= n; j++)
		{
			c[0][j] = 0;
		}
		int iidx = 0;
		for (int i=0;i<m;i++)
		{
			iidx++;
			String ismt = x[i];
			int jidx = 0;
			for (int j=0;j<n;j++)
			{
				jidx++;
				String jsmt = y[j];
				double sim = ComputeTwoStringDirectSimilarity(ismt, jsmt);
				if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
				{
					c[iidx][jidx] = c[iidx - 1][jidx - 1] + 1;
				} else if (c[iidx - 1][jidx] >= c[iidx][jidx - 1]) {
					c[iidx][jidx] = c[iidx - 1][jidx];
				} else {
					c[iidx][jidx] = c[iidx][jidx - 1];
				}
			}
		}
		return 0.65*(c[m][n]*1.0)/(Math.min(m, n)*1.0) + 0.35*(c[m][n]*1.0)/(Math.max(m, n)*1.0);
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
		int max = Math.max(len_a, len_b);
		double dissimilar = 0.65*(distance*1.0)/(min*1.0) + 0.35*(distance*1.0)/(max*1.0);
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