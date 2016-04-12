package cn.yyx.contentassist.codesynthesis.data;

public class CSVariableHolderExtraInfo {
	
	private String varname = null;
	private Class<?> cls = null;
	
	public CSVariableHolderExtraInfo(String varname, Class<?> cls) {
		this.setVarname(varname);
		this.setCls(cls);
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}
	
	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	
}