package cn.yyx.contentassist.commonutils;

public class NameConvention {
	
	public static String GetAbbreviationOfType(String tp)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(tp.charAt(0));
		for (int i=1;i<tp.length();i++)
		{
			char c = tp.charAt(i);
			if (Character.isUpperCase(c))
			{
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}
	
}