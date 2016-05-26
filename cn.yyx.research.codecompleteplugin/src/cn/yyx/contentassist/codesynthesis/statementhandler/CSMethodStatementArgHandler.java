package cn.yyx.contentassist.codesynthesis.statementhandler;

public class CSMethodStatementArgHandler extends CSMethodStatementHandler {

	public CSMethodStatementArgHandler(CSMethodStatementHandler csh) {
		super(csh.getSete(), csh.getProb(), csh.getAoi());
		setNextstart(csh.getNextstart());
		setMostfar(csh.getMostfar());
		setSignals(csh.getSignals());
	}
	
}