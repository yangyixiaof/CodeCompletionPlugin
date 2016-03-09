package cn.yyx.contentassist.codeutils;

public class binaryOperator extends OneCode{
	
	String optr = null;
	
	public binaryOperator(String text) {
		this.optr = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof binaryOperator)
		{
			if (optr.equals(((binaryOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof binaryOperator)
		{
			if (optr.equals(((binaryOperator) t).optr))
			{
				return 1;
			}
		}
		return 0;
	}
	
}
