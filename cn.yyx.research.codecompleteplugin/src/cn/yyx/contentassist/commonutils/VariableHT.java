package cn.yyx.contentassist.commonutils;

import java.util.Map;

public class VariableHT {
	
	// private String holdertype = null;
	// private String holdername = null;
	// private int allvh = 0;
	private Map<String, String> tpvarname = null;
	private Map<String, Integer> tpremains = null;
	private int trimedscope = 0;
	
	public VariableHT(Map<String, String> tpvarname, Map<String, Integer> tpremains, int trimedscope) {
		this.setTpvarname(tpvarname);
		this.setTpremains(tpremains);
		this.setTrimedscope(trimedscope);
	}
	
	@Override
	public String toString() {
		return getTpvarname().toString();
	}

	public Map<String, String> getTpvarname() {
		return tpvarname;
	}

	public void setTpvarname(Map<String, String> tpvarname) {
		this.tpvarname = tpvarname;
	}

	public Map<String, Integer> getTpremains() {
		return tpremains;
	}

	public void setTpremains(Map<String, Integer> tpremains) {
		this.tpremains = tpremains;
	}

	public int getTrimedscope() {
		return trimedscope;
	}

	public void setTrimedscope(int trimedscope) {
		this.trimedscope = trimedscope;
	}
	
}