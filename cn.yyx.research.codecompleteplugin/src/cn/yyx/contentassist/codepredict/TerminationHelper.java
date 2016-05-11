package cn.yyx.contentassist.codepredict;

import cn.yyx.contentassist.codeutils.fullEndStatement;
import cn.yyx.contentassist.codeutils.leftBraceStatement;
import cn.yyx.contentassist.codeutils.rightBraceStatement;
import cn.yyx.contentassist.codeutils.statement;

public class TerminationHelper {
	
	public static boolean isTerminatedChar(char lastchar)
	{
		if (lastchar == ';' || lastchar == '}' || lastchar == '{') {
			return true;
		}
		return false;
	}
	
	public static boolean couldTerminate(Sentence sete, char lastchar)
	{
		statement smt = sete.getSmt();
		if (lastchar == ';' && (smt instanceof fullEndStatement))
		{
			return true;
		}
		if (lastchar == '}' && (smt instanceof rightBraceStatement))
		{
			return true;
		}
		if (lastchar == '{' && (smt instanceof leftBraceStatement))
		{
			return true;
		}
		return false;
	}
	
}