package cn.yyx.contentassist.codeutils;

public class codeHole extends identifier{
	
	public codeHole() {
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof codeHole)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof codeHole)
		{
			return 1;
		}
		return 0;
	}

}
