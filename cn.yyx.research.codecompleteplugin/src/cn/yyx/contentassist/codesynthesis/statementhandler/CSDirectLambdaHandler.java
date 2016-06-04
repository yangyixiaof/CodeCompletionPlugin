package cn.yyx.contentassist.codesynthesis.statementhandler;

import java.util.List;

public class CSDirectLambdaHandler extends CSStatementHandler {
	
	private List<String> tadnames = null;
	
	public CSDirectLambdaHandler(List<String> tadnames, CSStatementHandler cssh) {
		super(cssh.getSete(), cssh.getProb(), cssh.getAoi());
		this.setTadnames(tadnames);
	}

	public List<String> getTadnames() {
		return tadnames;
	}

	public void setTadnames(List<String> tadnames) {
		this.tadnames = tadnames;
	}
	
}