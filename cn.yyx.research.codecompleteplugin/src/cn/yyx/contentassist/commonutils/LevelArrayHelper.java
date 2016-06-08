package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;

public class LevelArrayHelper {
	
	public static void InitialIntArrayToZero(int[] iarr)
	{
		int i=0;
		int len = iarr.length;
		for (i=0;i<len;i++)
		{
			iarr[i] = 0;
		}
	}
	
	private static int CountAfterAndIncludeIdx(int[] iarr, int idx)
	{
		int count = 0;
		int len = iarr.length;
		for (int i=idx;i<len;i++)
		{
			count += iarr[i];
		}
		return count;
	}
	
	private static void IncOneToFirstSpare(int[] iarr, int idx)
	{
		int len = iarr.length;
		for (int i=idx;i<len;i++)
		{
			if (iarr[i] < PredictMetaInfo.OneFlowLineMaxTotalSuccess)
			{
				iarr[i]++;
				return;
			}
		}
	}
	
	public static boolean CheckAndAddOneAccordToLevel(int[] iarr, int level)
	{
		int realidx = Math.min(iarr.length-1, level);
		int max = (iarr.length - realidx)*PredictMetaInfo.OneFlowLineMaxTotalSuccess;
		int real = CountAfterAndIncludeIdx(iarr, realidx);
		if (real < max) {
			IncOneToFirstSpare(iarr, realidx);
		}
		if (real >= max) {
			return true;
		}
		return false;
	}
	
}