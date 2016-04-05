package cn.yyx.contentassist.codesynthesis;

public class CSMethodStatementHandler extends CSStatementHandler{
	
	private String methodname = null;
	
	public CSMethodStatementHandler(String methodname, CSStatementHandler csh) {
		super(csh.getSete(), csh.getProb());
		this.setMethodname(methodname);
	}
	
	public String getMethodname() {
		return methodname;
	}
	
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	
}