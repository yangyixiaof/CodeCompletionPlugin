package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.methodInvocationStatement;
import cn.yyx.contentassist.codeutils.statement;

public class StatementsMIs {
	
	private List<methodInvocationStatement> smis = new LinkedList<methodInvocationStatement>();
	private List<statement> smts = new LinkedList<statement>();
	
	public StatementsMIs(List<methodInvocationStatement> smis, List<statement> smts) {
		this.setSmis(smis);
		this.setSmts(smts);
	}

	public List<methodInvocationStatement> getSmis() {
		return smis;
	}

	public void setSmis(List<methodInvocationStatement> smis) {
		this.smis = smis;
	}

	public List<statement> getSmts() {
		return smts;
	}

	public void setSmts(List<statement> smts) {
		this.smts = smts;
	}
	
}