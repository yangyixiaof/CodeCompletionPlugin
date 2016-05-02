package cn.yyx.contentassist.codesynthesis;

public class ErrorCheck {
	
	public static void NoGenerationCheck(String errorinfo)
	{
		new Exception(errorinfo + " need to be generated? What the fuck! Serious error, the system will exit.").printStackTrace();
		System.exit(1);
	}
	
}
