package cn.yyx.contentassist.codesynthesis.typeutil;

import cn.yyx.contentassist.specification.TypeMember;

public class CCType implements Cloneable {
	
	private Class<?> cls = null;
	private String clstr = null;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new CCType(getCls(), getClstr());
	}
	
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
		return clstr;
	}
	
}