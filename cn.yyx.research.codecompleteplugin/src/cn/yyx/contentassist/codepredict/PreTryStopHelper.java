package cn.yyx.contentassist.codepredict;

import cn.yyx.contentassist.codeutils.fullEndStatement;
import cn.yyx.contentassist.codeutils.leftBraceStatement;
import cn.yyx.contentassist.codeutils.rightBraceStatement;
import cn.yyx.contentassist.codeutils.statement;

public class PreTryStopHelper {
	
	public static boolean IsStopSentence(Sentence sete)
	{
		return IsStopStatement(sete.getSmt());
	}
	
	public static boolean IsStopStatement(statement smt)
	{
		if (smt instanceof rightBraceStatement || smt instanceof fullEndStatement || smt instanceof leftBraceStatement)
		{
			return true;
		}
		return false;
	}
	
}