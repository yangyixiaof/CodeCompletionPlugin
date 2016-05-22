package cn.yyx.contentassist.commonutils;

public class VariableHT {
	
	private String holdertype = null;
	private String holdername = null;
	private int allvh = 0;
	
	public VariableHT(String holdertype, String holdername, int allvh) {
		this.setHoldertype(holdertype);
		this.setHoldername(holdername);
		this.setAllvh(allvh);
	}

	public VariableHT() {
	}

	public String getHoldertype() {
		return holdertype;
	}

	public void setHoldertype(String holdertype) {
		this.holdertype = holdertype;
	}

	public String getHoldername() {
		return holdername;
	}

	public void setHoldername(String holdername) {
		this.holdername = holdername;
	}
	
	@Override
	public String toString() {
		return "holdertype:" + holdertype + ";holdername:" + holdername;
	}

	public int getAllvh() {
		return allvh;
	}

	public void setAllvh(int allvh) {
		this.allvh = allvh;
	}
	
}