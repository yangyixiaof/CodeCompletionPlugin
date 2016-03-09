package cn.yyx.contentassist.codeutils;

public class characterLiteral extends literal{
	
	String literal = null;
	
	public characterLiteral(String text) {
		this.literal = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof characterLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof characterLiteral)
		{
			return 0.6 + 0.4*(literal.equals(((characterLiteral) t).literal) ? 1 : 0);
		}
		return 0;
	}

}
