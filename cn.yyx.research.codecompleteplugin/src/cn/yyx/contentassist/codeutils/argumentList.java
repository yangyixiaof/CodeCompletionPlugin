package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

public class argumentList {
	
	private List<referedExpression> el = new LinkedList<referedExpression>();
	
	public argumentList() {
	}
	
	public void AddToFirst(referedExpression re)
	{
		getEl().add(0, re);
	}
	
	public void AddReferedExpression(referedExpression re)
	{
		getEl().add(re);
	}

	public List<referedExpression> getEl() {
		return el;
	}

	public void setEl(List<referedExpression> el) {
		this.el = el;
	}
	
}
