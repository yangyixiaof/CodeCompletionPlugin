package cn.yyx.contentassist.codesynthesis.statementhandler;

public class CSInnerLevelPreHandler extends CSStatementHandler {
	
	private String whichinner = null; // if/while/enhanced-for/do-while, so on, just for print information.
	
	public CSInnerLevelPreHandler(String whichinner, CSStatementHandler sh) {
		super(sh.getSete(), sh.getProb(), sh.getAoi());
		this.setWhichinner(whichinner);
	}

	public String getWhichinner() {
		return whichinner;
	}

	public void setWhichinner(String whichinner) {
		this.whichinner = whichinner;
	}
	
}