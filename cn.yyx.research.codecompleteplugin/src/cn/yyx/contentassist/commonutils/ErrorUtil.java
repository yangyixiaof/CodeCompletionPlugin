package cn.yyx.contentassist.commonutils;

public class ErrorUtil {
	
	public static void ErrorAndStop(String errorinfo)
	{
		System.err.println(errorinfo);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
	public static void CheckDirectlyMemberHintInAINotNull(AdditionalInfo ai)
	{
		if (ai == null || (ai.getDirectlyMemberHint() == null))
		{
			System.err.println("What the fuck! in commonVarRef related ditrctlyMemberHint or MemberType can not be null.");
			new Exception().printStackTrace();
			System.exit(1);
		}
	}
	
	public static void CanNeverReachHere(String info)
	{
		System.err.println(info);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
}