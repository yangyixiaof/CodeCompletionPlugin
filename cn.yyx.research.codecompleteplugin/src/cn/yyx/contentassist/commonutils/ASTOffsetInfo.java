package cn.yyx.contentassist.commonutils;

public class ASTOffsetInfo {
	
	private boolean isInAnonymousClass = false;
	private boolean isInFieldLevel = false;
	
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
	
}