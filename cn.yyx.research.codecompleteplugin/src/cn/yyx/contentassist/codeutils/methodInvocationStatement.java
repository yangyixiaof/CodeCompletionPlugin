package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class methodInvocationStatement extends expressionStatement{
	
	identifier name = null;
	argumentList argList = null;
	
	public methodInvocationStatement(identifier name, argumentList argList) {
		this.name = name;
		this.argList = argList;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodInvocationStatement)
		{
			if (name.CouldThoughtSame(((methodInvocationStatement) t).name) || argList.CouldThoughtSame(((methodInvocationStatement) t).argList))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodInvocationStatement)
		{
			return 0.3 + 0.7*(0.5*(name.Similarity(((methodInvocationStatement) t).name)) + 0.5*(argList.Similarity(((methodInvocationStatement) t).argList)));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}