package cn.yyx.contentassist.commonutils;

import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class SynthesisHandler {
	
	private ScopeOffsetRefHandler handler = null;
	private ContextHandler ch = null;
	
	public SynthesisHandler(ScopeOffsetRefHandler handler, ContextHandler ch) {
		this.setHandler(handler);
		this.setCh(ch);
	}

	public ScopeOffsetRefHandler getHandler() {
		return handler;
	}

	public void setHandler(ScopeOffsetRefHandler handler) {
		this.handler = handler;
	}

	public ContextHandler getCh() {
		return ch;
	}

	public void setCh(ContextHandler ch) {
		this.ch = ch;
	}
	
}
