package cn.yyx.contentassist.codeutils;

public class stringLiteral extends literal{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof stringLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof stringLiteral)
		{
			return 1;
		}
		return 0;
	}

}
