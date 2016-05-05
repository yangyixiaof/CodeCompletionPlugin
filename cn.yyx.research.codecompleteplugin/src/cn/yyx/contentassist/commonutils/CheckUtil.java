package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

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
	
	public static void ErrorAndStop(String errorinfo)
	{
		System.err.println(errorinfo);
		new Exception().printStackTrace();
		System.exit(1);
	}
	
	/*public static void CheckDirectlyMemberHintInAINotNull(AdditionalInfo ai)
	{
		if (ai == null || (ai.getDirectlyMemberHint() == null))
		{
			System.err.println("What the fuck! in commonVarRef related ditrctlyMemberHint or MemberType can not be null.");
			new Exception().printStackTrace();
			System.exit(1);
		}
	}*/
	
	public static void CanNeverReachHere(String info)
	{
		System.err.println(info);
		new Exception().printStackTrace();
		System.exit(1);
	}

	/*public static void CheckStatementHandlerIsArgTypeStatementHandler(CSStatementHandler smthandler) {
		if (!(smthandler instanceof CSArgTypeStatementHandler))
		{
			System.err.println("Input handler is not the expected CSMethodStatementHandler, what the fuck?");
			System.exit(1);
		}
	}*/
	
	/*public static void CheckStartNodeMustNotHaveAnyHoles(FlowLineNode<CSFlowLineData> startnode)
	{
		if (startnode.getData().isHashole())
		{
			System.err.println("What the fuck, start node in BackTraceGeneration has hole?. Serious error, the system will exit.");
			System.exit(1);
		}
	}*/
	
}