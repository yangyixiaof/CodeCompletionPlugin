package cn.yyx.contentassist.commonutils;

public class ErrorUtil {
	
	public static void ErrorAndStop(String errorinfo)
	{
		System.err.println(errorinfo);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
}
