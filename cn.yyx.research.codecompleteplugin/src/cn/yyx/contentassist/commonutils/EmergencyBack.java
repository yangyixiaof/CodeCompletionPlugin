package cn.yyx.contentassist.commonutils;

public class EmergencyBack {
	
	private int needlevel = -1;
	
	public EmergencyBack(int needlevel) {
		this.setNeedlevel(needlevel);
	}

	public int getNeedlevel() {
		return needlevel;
	}

	public void setNeedlevel(int needlevel) {
		this.needlevel = needlevel;
	}
	
}