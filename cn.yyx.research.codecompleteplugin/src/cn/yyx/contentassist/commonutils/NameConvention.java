package cn.yyx.contentassist.commonutils;

public class NameConvention {
	
	public static String GetAbbreviationOfType(String tp)
	{
		String end = "";
		int ts = StringUtil.CountHappenTimes(tp, '[');
		if (ts > 0)
		{
			end = "Arr" + ts;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(tp.charAt(0));
		for (int i=1;i<tp.length();i++)
		{
			char c = tp.charAt(i);
			if (Character.isUpperCase(c))
			{
				sb.append(Character.toLowerCase(c));
			}
			if (c == '.' && i+1 < tp.length())
			{
				sb.append(tp.charAt(i+1));
				if (i % 2 == 0 || i % 3 == 0)
				{
					sb.append(tp.charAt(i-1));
				}
			}
		}
		sb.append(end);
		return sb.toString();
	}
	
}