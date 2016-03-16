package cn.yyx.contentassist.commonutils;

import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class SynthesisHandler {
	
	private ScopeOffsetRefHandler handler = null;
	private ContextHandler ch = null;
	
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
	
}
