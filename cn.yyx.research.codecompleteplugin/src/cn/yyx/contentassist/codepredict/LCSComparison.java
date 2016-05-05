package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codeutils.argType;
import cn.yyx.contentassist.codeutils.statement;

public class LCSComparison {
	
	public static double LCSSimilarityOneCode(List<argType> x, List<argType> y) {
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
		return c[m][n]/(Math.min(m, n)*1.0);
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
		return c[m][n]/(Math.min(m, n)*1.0);
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
		return c[m][n]/(Math.min(m, n)*1.0);
	}
	
}