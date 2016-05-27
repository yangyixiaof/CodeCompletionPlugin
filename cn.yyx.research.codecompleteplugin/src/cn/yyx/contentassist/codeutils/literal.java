package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public abstract class literal extends referedExpression{
	
	public abstract void HandleNegativeOperator() throws CodeSynthesisException;
	
}