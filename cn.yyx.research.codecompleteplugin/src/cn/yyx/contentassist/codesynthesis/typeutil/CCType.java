package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.specification.TypeMember;

public class CCType {
	
	private Class<?> cls = null;
	private String clstr = null;
	
	public CCType(Class<?> cls, String clstr) {
		setCls(cls);
		setClstr(clstr);
	}
	
	public CCType(TypeMember tm) {
		setCls(tm.getTypeclass());
		setClstr(tm.getType());
	}
	
	public boolean HasProperty(Class<?> cls)
	{
		if (getCls() != null && cls.isAssignableFrom(getCls()))
		{
			return true;
		}
		return false;
	}
	
	public static LinkedList<CCType> CCTypeList(List<TypeMember> tmlist) {
		LinkedList<CCType> result = new LinkedList<CCType>();
		Iterator<TypeMember> itr = tmlist.iterator();
		while (itr.hasNext())
		{
			TypeMember tm = itr.next();
			result.add(new CCType(tm));
		}
		return result;
	}

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
		return TypeCheckHelper.NormalizeClass(cls).isAssignableFrom(TypeCheckHelper.NormalizeClass(clspara.getCls()));
	}
	
	@Override
	public String toString() {
		return "cls:" + cls + ";clstr:" + clstr;
	}
	
}