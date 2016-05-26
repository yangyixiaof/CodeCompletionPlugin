package cn.yyx.contentassist.codesynthesis.statementhandler;

public class CSMethodStatementFirstArgHandler extends CSMethodStatementHandler {

	public CSMethodStatementFirstArgHandler(CSMethodStatementHandler csh) {
		super(csh.getSete(), csh.getProb(), csh.getAoi());
		setNextstart(csh.getNextstart());
		setMostfar(csh.getMostfar());
		setSignals(csh.getSignals());
	}
	
}