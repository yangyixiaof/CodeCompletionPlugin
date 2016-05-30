package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;

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
	
	public static int CountHappenTimes(String content, char needcount)
	{
		int allcount = 0;
		int len = content.length();
		for (int i=0;i<len;i++)
		{
			char t = content.charAt(i);
			if (t == needcount)
			{
				allcount++;
			}
		}
		return allcount;
	}
	
	public static String ExtractParameterizedFromRawType(String rawtype)
	{
		if (CodeCompletionMetaInfo.DebugMode)
		{
			System.err.println("rawtype:" + rawtype);
		}
		int lidx = rawtype.indexOf('<');
		if (lidx < 0)
		{
			return "Object";
		}
		else
		{
			int ridx = rawtype.lastIndexOf('>');
			return rawtype.substring(lidx+1, ridx);
		}
	}
	
	public static String GenerateDuplicates(String onepiece, int times)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<times;i++)
		{
			sb.append(onepiece);
		}
		return sb.toString();
	}
	
}