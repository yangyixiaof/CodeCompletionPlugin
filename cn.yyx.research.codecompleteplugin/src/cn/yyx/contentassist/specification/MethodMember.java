package cn.yyx.contentassist.specification;

import java.util.LinkedList;

public class MethodMember {
	
	private String name = null;
	private String returntype = null;
	private String whereDeclared = null;
	private LinkedList<String> argtypelist = null;
	private LinkedList<String> argnamelist = null;
	
	public MethodMember(String name, String returntype, String whereDeclared, LinkedList<String> argnamelist, LinkedList<String> argtypelist) {
		this.name = name;
		this.returntype = returntype;
		this.whereDeclared = whereDeclared;
		this.argnamelist = argnamelist;
		this.argtypelist = argtypelist;
	}
	
	@Override
	public String toString() {
		String args = "";
		int len = getArgtypelist().size();
		for (int i=0;i<len;i++)
		{
			String type = getArgtypelist().get(i);
			String name = getArgnamelist().get(i);
			args += "#" + type + "$" + name + "#";
		}
		return getReturntype() + " " + getName() + args + " - " + getWhereDeclared();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturntype() {
		return returntype;
	}

	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}

	public String getWhereDeclared() {
		return whereDeclared;
	}

	public void setWhereDeclared(String whereDeclared) {
		this.whereDeclared = whereDeclared;
	}

	public LinkedList<String> getArgtypelist() {
		return argtypelist;
	}

	public void setArgtypelist(LinkedList<String> argtypelist) {
		this.argtypelist = argtypelist;
	}

	public LinkedList<String> getArgnamelist() {
		return argnamelist;
	}

	public void setArgnamelist(LinkedList<String> argnamelist) {
		this.argnamelist = argnamelist;
	}
	
}