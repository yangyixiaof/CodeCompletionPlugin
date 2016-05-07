package cn.yyx.contentassist.codesynthesis.typeutil;

public class CCType {
	
	private Class<?> cls = null;
	private String clsstr = null;
	
	public CCType(Class<?> cls, String clsstr) {
		this.setCls(cls);
		this.setClsstr(clsstr);
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public String getClsstr() {
		return clsstr;
	}

	public void setClsstr(String clsstr) {
		this.clsstr = clsstr;
	}
	
}