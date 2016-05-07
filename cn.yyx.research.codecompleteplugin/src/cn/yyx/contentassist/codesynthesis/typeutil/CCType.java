package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.yyx.contentassist.specification.TypeMember;

public class CCType {
	
	private List<Class<?>> clss = new LinkedList<Class<?>>();
	private List<String> clsstr = new LinkedList<String>();
	private Set<Integer> valididx = new TreeSet<Integer>();
	
	public CCType() {
	}
	
	public CCType(TypeMember tm) {
		clss.add(tm.getTypeclass());
		clsstr.add(tm.getType());
	}
	
	public CCType(List<TypeMember> tmlist) {
		Iterator<TypeMember> itr = tmlist.iterator();
		while (itr.hasNext())
		{
			TypeMember tm = itr.next();
			clss.add(tm.getTypeclass());
			clsstr.add(tm.getType());
		}
	}

	public void AddPossibleClass(Class<?> cls, String clstr)
	{
		getClss().add(cls);
		getClsstr().add(clstr);
	}
	
	public List<Class<?>> getClss() {
		return clss;
	}

	public void setClss(List<Class<?>> clss) {
		this.clss = clss;
	}

	public List<String> getClsstr() {
		return clsstr;
	}

	public void setClsstr(List<String> clsstr) {
		this.clsstr = clsstr;
	}

	public Set<Integer> getValididx() {
		return valididx;
	}

	public void setValididx(Set<Integer> valididx) {
		this.valididx = valididx;
	}
	
}