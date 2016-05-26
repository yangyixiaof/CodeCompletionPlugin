package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class CheckUtil {
	
	public static void CheckRefSame(Object ref1, Object ref2, String info)
	{
		if (ref1 != ref2)
		{
			System.out.println(info);
			System.exit(1);
		}
	}
	
	public static void CheckNotNull(Object ref, String info)
	{
		if (ref == null)
		{
			System.err.println(info);
			System.exit(1);
		}
	}
	
	public static void CheckMustNull(Object ref, String info)
	{
		if (ref != null)
		{
			System.err.println(info);
			System.exit(1);	
		}
	}
	
	public static void CheckStatementHandlerIsMethodStatementHandler(CSStatementHandler smthandler)
	{
		if (!(ClassInstanceOfUtil.ObjectInstanceOf(smthandler, CSMethodStatementHandler.class)))
		{
			System.err.println("Input handler is not the expected CSMethodStatementHandler, what the fuck?");
			System.exit(1);
		}
	}
	
	public static void ErrorAndStop(String errorinfo)
	{
		System.err.println(errorinfo);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
	public static void CanNeverReachHere(String info)
	{
		System.err.println(info);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
}