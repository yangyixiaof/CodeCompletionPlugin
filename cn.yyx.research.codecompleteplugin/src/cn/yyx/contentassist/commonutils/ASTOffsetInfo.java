package cn.yyx.contentassist.commonutils;

public class ASTOffsetInfo {
	
	private boolean isInAnonymousClass = false;
	private boolean isInFieldLevel = false;
	private String indent = null;
	
	public ASTOffsetInfo() {
	}

	public boolean isInAnonymousClass() {
		return isInAnonymousClass;
	}

	public void setInAnonymousClass(boolean isInAnonymousClass) {
		this.isInAnonymousClass = isInAnonymousClass;
	}

	public boolean isInFieldLevel() {
		return isInFieldLevel;
	}

	public void setInFieldLevel(boolean isInFieldLevel) {
		this.isInFieldLevel = isInFieldLevel;
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}
	
	@Override
	public String toString() {
		return "isInAnonymousClass:" + isInAnonymousClass + ";isInFieldLevel:" + isInFieldLevel + ";indent:" + indent;
	}
	
}