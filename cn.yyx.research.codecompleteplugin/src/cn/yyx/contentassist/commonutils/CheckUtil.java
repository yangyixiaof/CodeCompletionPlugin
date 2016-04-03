package cn.yyx.contentassist.commonutils;

public class CheckUtil {
	
	public static void CheckNotNull(Object ref, String info)
	{
		if (ref == null)
		{
			System.err.println(info);
			System.exit(1);
		}
	}
	
}
