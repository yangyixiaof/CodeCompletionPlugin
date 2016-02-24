package cn.yyx.research.language.JDTManager;

public class DataScopeInfo {
	private OneScope oscope = null;
	private String data = null;
	private String type = null;
	private boolean isFinal = false;
	private boolean isField = false;
	
	public DataScopeInfo(OneScope oscope, String data, String type, boolean isFinal, boolean isField) {
		this.oscope = oscope;
		this.data = data;
		this.type = type;
		this.isFinal = isFinal;
		this.isField = isField;
	}
	
	public OneScope getOscope() {
		return oscope;
	}
	public void setOscope(OneScope oscope) {
		this.oscope = oscope;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	public boolean isField() {
		return isField;
	}
	public void setField(boolean isField) {
		this.isField = isField;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
