package cn.yyx.research.language.Utility;

public class MyLogger {
	
	public static boolean PrintInfo = false;
	
	public static void Info(String info)
	{
		if (PrintInfo)
		{
			System.out.println(info);
		}
	}
	
	public static void Error(String error)
	{
		if (PrintInfo)
		{
			System.err.println(error);
		}
	}
	
}