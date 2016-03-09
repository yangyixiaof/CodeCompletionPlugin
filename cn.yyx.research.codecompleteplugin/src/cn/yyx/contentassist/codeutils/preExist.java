package cn.yyx.contentassist.codeutils;

public class preExist extends identifier{

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof preExist)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof preExist)
		{
			return 1;
		}
		return 0;
	}

}
