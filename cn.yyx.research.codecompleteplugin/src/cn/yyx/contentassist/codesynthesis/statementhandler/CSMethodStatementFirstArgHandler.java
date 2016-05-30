package cn.yyx.contentassist.codesynthesis.statementhandler;

public class CSMethodStatementFirstArgHandler extends CSMethodStatementHandler {
	
	private CSMethodStatementHandler csh = null;
	
	public CSMethodStatementFirstArgHandler(CSMethodStatementHandler csh) {
		super(csh.getSete(), csh.getProb(), csh.getAoi());
		this.setCsh(csh);
		// setNextstart(csh.getNextstart());
		// setMostfar(csh.getMostfar());
		// setSignals(csh.getSignals());
	}

	public CSMethodStatementHandler getCsh() {
		return csh;
	}

	public void setCsh(CSMethodStatementHandler csh) {
		this.csh = csh;
	}
	
}