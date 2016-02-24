package cn.yyx.research.language.JDTManager;

public class OffsetLibrary {
	
	public static final int OffsetBlock = 1;
	
	public static String GetOffsetDescription(Integer offset)
	{
		//"$" + 
		String result = null;
		if (offset != null)
		{
			result = Math.abs(offset)+"";
		}
		//(offset <= 1 ? "aj#" : (offset/OffsetBlock)+"#")
		if (result == null)
		{
			System.err.println("What the fuck? offset in this function can not be null.");
			System.exit(1);
		}
		return result;
	}
	
	public static String GetAdjacentRefCode(boolean ispositive)
	{
		String pre = "";
		if (ispositive)
		{
			pre = "$+";
		}
		else
		{
			pre = "$-";
		}
		return pre + "aj#";
	}
	
	/*public static String GetOffsetDescription(int dataline, int currline)
	{
		if (dataline == GCodeMetaInfo.OutofScopeVarOrObject)
		{
			return "INF#";
		}
		return GetOffsetDescription(dataline-currline);
	}*/
	
}