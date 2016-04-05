package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;

public class CheckUtil {
	
	public static void CheckNotNull(Object ref, String info)
	{
		if (ref == null)
		{
			System.err.println(info);
			System.exit(1);
		}
	}
	
	public static void CheckStatementHandlerIsMethodStatementHandler(CSStatementHandler smthandler)
	{
		if (!(smthandler instanceof CSMethodStatementHandler))
		{
			System.err.println("Input handler is not the expected CSMethodStatementHandler, what the fuck?");
			System.exit(1);
		}
	}
	
}
