package cn.yyx.contentassist.commonutils;

public class VariableHT {
	
	private String holdertype = null;
	private String holdername = null;
	
	public VariableHT(String holdertype, String holdername) {
		this.setHoldertype(holdertype);
		this.setHoldername(holdername);
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
	
}