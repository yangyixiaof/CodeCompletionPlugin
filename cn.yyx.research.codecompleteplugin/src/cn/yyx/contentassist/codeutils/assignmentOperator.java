package cn.yyx.contentassist.codeutils;

public class assignmentOperator extends OneCode{
	
	String optr = null;
	
	public assignmentOperator(String text) {
		this.optr = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof assignmentOperator)
		{
			if (optr.equals(((assignmentOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof assignmentOperator)
		{
			if (optr.equals(((assignmentOperator) t).optr))
			{
				return 1;
			}
		}
		return 0;
	}

}
