package cn.yyx.contentassist.commonutils;

import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class SynthesisHandler {
	
	private ScopeOffsetRefHandler handler = null;
	private ContextHandler ch = null;
	private AdditionalInfo ai = new AdditionalInfo();
	private String recenttype = null;
	
	public SynthesisHandler(ScopeOffsetRefHandler handler, ContextHandler ch) {
		this.setScopeOffsetRefHandler(handler);
		this.setContextHandler(ch);
	}

	public ScopeOffsetRefHandler getScopeOffsetRefHandler() {
		return handler;
	}

	public void setScopeOffsetRefHandler(ScopeOffsetRefHandler handler) {
		this.handler = handler;
	}

	public ContextHandler getContextHandler() {
		return ch;
	}

	public void setContextHandler(ContextHandler ch) {
		this.ch = ch;
	}

	public String getRecenttype() {
		return recenttype;
	}

	public void setRecenttype(String recenttype) {
		this.recenttype = recenttype;
	}

	public AdditionalInfo getAi() {
		return ai;
	}

	public void setAi(AdditionalInfo ai) {
		this.ai = ai;
	}
	
}
