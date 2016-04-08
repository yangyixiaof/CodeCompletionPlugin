package cn.yyx.contentassist.codesynthesis;

public class CSArgTypeStatementHandler extends CSStatementHandler{
	
	private char prec = 'A';
	
	public CSArgTypeStatementHandler(char prec, CSStatementHandler csh) {
		super(csh.getSete(), csh.getProb());
		this.setPrec(prec);
	}

	public char getPrec() {
		return prec;
	}

	public void setPrec(char prec) {
		this.prec = prec;
	}

	public char GetAndIncreaseChar() {
		char pre = prec;
		prec++;
		return pre;
	}

}
