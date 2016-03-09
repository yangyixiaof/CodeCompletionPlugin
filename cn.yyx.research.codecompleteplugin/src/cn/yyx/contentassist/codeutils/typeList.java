package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import SJ8Parse.Java8Parser.TypeContext;

public class typeList extends OneCode {
	
	private List<TypeContext> el = new LinkedList<TypeContext>();
	
	public typeList() {
	}
	
	public void AddToFirst(TypeContext re)
	{
		getEl().add(0, re);
	}
	
	public void AddReferedExpression(TypeContext re)
	{
		getEl().add(re);
	}

	public List<TypeContext> getEl() {
		return el;
	}

	public void setEl(List<TypeContext> el) {
		this.el = el;
	}
}
