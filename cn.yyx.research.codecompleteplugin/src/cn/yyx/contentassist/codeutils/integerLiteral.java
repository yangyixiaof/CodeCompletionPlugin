package cn.yyx.contentassist.codeutils;

public class integerLiteral extends numberLiteral{

	int value = -1;
	
	public integerLiteral(int parseInt) {
		this.value = parseInt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof integerLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof integerLiteral)
		{
			return 1;
		}
		return 0;
	}

}
