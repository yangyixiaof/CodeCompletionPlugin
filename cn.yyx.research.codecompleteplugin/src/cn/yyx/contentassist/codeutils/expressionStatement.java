package cn.yyx.contentassist.codeutils;

public abstract class expressionStatement extends statement{
	
	protected boolean arrayAccessEnd = false;

	public boolean isArrayAccessEnd() {
		return arrayAccessEnd;
	}

	public void setArrayAccessEnd(boolean arrayAccessEnd) {
		this.arrayAccessEnd = arrayAccessEnd;
	}
	
}
