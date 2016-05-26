package cn.yyx.contentassist.codepredict;

import cn.yyx.contentassist.codeutils.commonOverStatement;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;

public class TerminationHelper {
	
	public static boolean isTerminatedChar(char lastchar)
	{
		if (lastchar == ';' || lastchar == '}' || lastchar == '{') {
			return true;
		}
		return false;
	}
	
	public static boolean couldTerminate(Sentence sete, char lastchar, int currlen, int totallen, boolean isexactmatch)
	{
		int level = (int)(totallen*0.75);
		statement smt = sete.getSmt();
		boolean minilevel = currlen >= level;
		if (isexactmatch)
		{
			minilevel = currlen >= totallen;
		}
		if (lastchar == ';' && (ClassInstanceOfUtil.ObjectInstanceOf(smt, commonOverStatement.class)) && minilevel)
		{
			return true;
		}
		return false;
	}
	
}