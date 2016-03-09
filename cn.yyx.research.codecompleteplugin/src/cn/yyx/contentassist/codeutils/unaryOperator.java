package cn.yyx.contentassist.codeutils;

public class unaryOperator extends OneCode{
	
	String optr = null;
	
	public unaryOperator(String text) {
		this.optr = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof unaryOperator)
		{
			if (optr.equals(((unaryOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof unaryOperator)
		{
			double prob = 0;
			if (optr.equals(((unaryOperator) t).optr))
			{
				prob = 1;
			}
			return 0.5 + 0.5 * prob;
		}
		return 0;
	}
	
}