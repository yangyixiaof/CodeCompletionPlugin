package cn.yyx.contentassist.codeutils;

public class booleanLiteral extends literal{
	
	boolean value = false;
	
	public booleanLiteral(boolean parseBoolean) {
		this.value = parseBoolean;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof booleanLiteral)
		{
			if (value == ((booleanLiteral)t).value)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof booleanLiteral)
		{
			if (value == ((booleanLiteral)t).value)
			{
				return 1;
			}
		}
		return 0;
	}

}
