package cn.yyx.contentassist.codeutils;

public class fieldAccess extends referedExpression{
	
	identifier name = null;
	referedExpression rexp = null;
	
	public fieldAccess(identifier name, referedExpression rexp) {
		this.name = name;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return 0.4 + 0.6*(0.6*name.Similarity(((fieldAccess) t).name) + 0.4*(rexp.Similarity(((fieldAccess) t).rexp)));
		}
		return 0;
	}
	
}
