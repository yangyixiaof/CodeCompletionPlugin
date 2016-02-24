package cn.yyx.research.language.Utility;

public class SleepTimeController {
	
	public static void TImeSleep(long begin, long end) throws InterruptedException
	{
		long gap = begin - end;
		if (gap >= 200 && gap <= 300)
		{
			Thread.sleep(500);
		}
		if (gap >= 301 && gap <=500)
		{
			Thread.sleep(1000);
		}
		if (gap >= 501 && gap <=600)
		{
			System.gc();
			Thread.sleep(1500);
		}
		if (gap >= 601 && gap <=800)
		{
			System.gc();
			Thread.sleep(2000);
		}
		if (gap >= 801 && gap <=1000)
		{
			System.gc();
			Thread.sleep(2500);
		}
		if (gap >= 1001 && gap <= 2001)
		{
			System.gc();
			Thread.sleep(5000);
		}
		if (gap >= 2001)
		{
			System.gc();
			Thread.sleep(10000);
		}
	}
	
}
