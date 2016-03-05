package cn.yyx.contentassist.codeutils;

public class floatingPointLiteral extends numberLiteral{
	
	double value = -1;
	
	public floatingPointLiteral(double parseDouble) {
		this.value = parseDouble;
	}

}
