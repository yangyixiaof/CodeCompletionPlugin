package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.statement;

public class StatementsMIs {
	
	private List<statement> smis = new LinkedList<statement>();
	private List<statement> smts = new LinkedList<statement>();
	
	public StatementsMIs(List<statement> smis, List<statement> smts) {
		this.setSmis(smis);
		this.setSmts(smts);
	}

	public List<statement> getSmis() {
		return smis;
	}

	public void setSmis(List<statement> smis) {
		this.smis = smis;
	}

	public List<statement> getSmts() {
		return smts;
	}

	public void setSmts(List<statement> smts) {
		this.smts = smts;
	}
	
}