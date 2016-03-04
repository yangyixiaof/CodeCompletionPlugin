package cn.yyx.contentassist.codeutils;

public class fieldAccessStatement extends expressionStatement{
	
	fieldAccess fa = null;
	
	public fieldAccessStatement(fieldAccess fa) {
		this.fa = fa;
	}

}
