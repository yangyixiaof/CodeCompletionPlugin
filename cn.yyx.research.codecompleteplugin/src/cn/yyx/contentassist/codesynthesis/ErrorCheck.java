package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class ErrorCheck {
	
	public static void NoGenerationCheck(String errorinfo) throws CodeSynthesisException
	{
		System.err.println(errorinfo + " need to be generated? What the fuck! Serious error, the system will exit.");
		throw new CodeSynthesisException(errorinfo + " need to be generated? What the fuck! Serious error, the system will exit.");
		// System.exit(1);
	}
	
}
