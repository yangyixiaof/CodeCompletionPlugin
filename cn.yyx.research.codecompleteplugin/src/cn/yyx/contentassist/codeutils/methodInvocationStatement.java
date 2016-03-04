package cn.yyx.contentassist.codeutils;

public class methodInvocationStatement extends expressionStatement{
	
	identifier name = null;
	argumentList argList = null;
	
	public methodInvocationStatement(identifier name, argumentList argList) {
		this.name = name;
		this.argList = argList;
	}

}
