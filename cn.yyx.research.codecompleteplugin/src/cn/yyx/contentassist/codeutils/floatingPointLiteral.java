package cn.yyx.contentassist.codeutils;

public class floatingPointLiteral extends numberLiteral{
	
	double value = -1;
	
	public floatingPointLiteral(double parseDouble) {
		this.value = parseDouble;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof floatingPointLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof floatingPointLiteral)
		{
			return 1;
		}
		return 0;
	}

}
