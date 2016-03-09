package cn.yyx.contentassist.codeutils;

public class nullLiteral extends literal{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof nullLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof nullLiteral)
		{
			return 1;
		}
		return 0;
	}

}
