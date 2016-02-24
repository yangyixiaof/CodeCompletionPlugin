package cn.yyx.research.language.JDTManager;

public class EnteredLambdaParamFlag {
	
	//This is not a stack actually.
	private boolean isInLambdaParam = false;
	
	public EnteredLambdaParamFlag() {
	}
	
	public boolean IsInLambda()
	{
		return isInLambdaParam;
	}
	
	public void pop()
	{
		isInLambdaParam = false;
	}
	
	public void push(int blockid)
	{
		// System.out.println("pushed id: class : "+blockid);
		isInLambdaParam = true;
	}
	
}