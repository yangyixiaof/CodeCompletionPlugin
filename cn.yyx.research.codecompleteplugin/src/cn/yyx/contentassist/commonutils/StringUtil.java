package cn.yyx.contentassist.commonutils;

public class StringUtil {
	
	public static String GetContentBehindFirstWhiteSpace(String cnt)
	{
		int idx = cnt.indexOf(" ");
		if (idx == -1)
		{
			return cnt;
		}
		else
		{
			return cnt.substring(idx+1);
		}
	}
	
}
