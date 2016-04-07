package cn.yyx.contentassist.codesynthesis;

public class CSFieldAccessStatementHandler extends CSStatementHandler{
	
	private String field = null;
	
	private boolean fieldused = false;
	
	public CSFieldAccessStatementHandler(String field, CSStatementHandler csh) {
		super(csh.getSete(), csh.getProb());
		this.setField(field);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean isFieldused() {
		return fieldused;
	}

	public void setFieldunused(boolean fieldunused) {
		this.fieldused = fieldunused;
	}
	
}