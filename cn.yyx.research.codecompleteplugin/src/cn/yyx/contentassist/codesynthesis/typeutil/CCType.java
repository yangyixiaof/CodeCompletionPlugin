package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.specification.TypeMember;

public class CCType {
	
	private Class<?> cls = null;
	private String clstr = null;
	// private List<Class<?>> clss = new LinkedList<Class<?>>();
	// private List<String> clsstr = new LinkedList<String>();
	// private Set<Integer> valididx = new TreeSet<Integer>();
	
	public CCType(Class<?> cls, String clstr) {
		setCls(cls);
		setClstr(clstr);
	}
	
	public CCType(TypeMember tm) {
		setCls(tm.getTypeclass());
		setClstr(tm.getType());
	}
	
	public static LinkedList<CCType> CCTypeList(List<TypeMember> tmlist) {
		LinkedList<CCType> result = new LinkedList<CCType>();
		Iterator<TypeMember> itr = tmlist.iterator();
		while (itr.hasNext())
		{
			TypeMember tm = itr.next();
			result.add(new CCType(tm));
			// clss.add(tm.getTypeclass());
			// clsstr.add(tm.getType());
		}
		return result;
	}

	/*public void AddPossibleClass(Class<?> cls, String clstr)
	{
		getClss().add(cls);
		getClsstr().add(clstr);
	}*/

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public String getClstr() {
		return clstr;
	}

	public void setClstr(String clstr) {
		this.clstr = clstr;
	}

	public boolean isAssignableFrom(CCType clspara) {
		return cls.isAssignableFrom(clspara.getCls());
	}
	
	/*public List<Class<?>> getClss() {
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
	}*/
	
}