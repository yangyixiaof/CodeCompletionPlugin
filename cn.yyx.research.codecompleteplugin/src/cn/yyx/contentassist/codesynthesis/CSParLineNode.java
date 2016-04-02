package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSParLineNode {
	
	private String data = null;
	private Class<?> dcls = null;
	private boolean hashole = false;
	private SynthesisHandler handler = null;
	private CSParLineNode prev = null;
	private CSParLineNode next = null;
	private CSParLineNode silbprev = null;
	private CSParLineNode silbnext = null;
	
	public CSParLineNode(SynthesisHandler handler) {
		this.setHandler(handler);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Class<?> getDcls() {
		return dcls;
	}

	public void setDcls(Class<?> dcls) {
		this.dcls = dcls;
	}

	public boolean isHashole() {
		return hashole;
	}

	public void setHashole(boolean hashole) {
		this.hashole = hashole;
	}

	public SynthesisHandler getHandler() {
		return handler;
	}

	public void setHandler(SynthesisHandler handler) {
		this.handler = handler;
	}

	public CSParLineNode getPrev() {
		return prev;
	}

	public void setPrev(CSParLineNode prev) {
		this.prev = prev;
	}

	public CSParLineNode getNext() {
		return next;
	}

	public void setNext(CSParLineNode next) {
		this.next = next;
	}

	public CSParLineNode getSilbprev() {
		return silbprev;
	}

	public void setSilbprev(CSParLineNode silbprev) {
		this.silbprev = silbprev;
	}

	public CSParLineNode getSilbnext() {
		return silbnext;
	}

	public void setSilbnext(CSParLineNode silbnext) {
		this.silbnext = silbnext;
	}
	
}
