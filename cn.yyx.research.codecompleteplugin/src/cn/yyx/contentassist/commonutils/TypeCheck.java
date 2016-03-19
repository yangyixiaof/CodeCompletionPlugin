package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;
import java.util.List;

public class TypeCheck {
	
	private String expreturntype = null;
	private Class<?> expreturntypeclass = null;
	private LinkedList<String> expargstypes = null;
	private LinkedList<Class<?>> expargstypesclasses = null;
	
	public String getExpreturntype() {
		return expreturntype;
	}
	public void setExpreturntype(String expreturntype) {
		this.expreturntype = expreturntype;
	}
	public Class<?> getExpreturntypeclass() {
		return expreturntypeclass;
	}
	public void setExpreturntypeclass(Class<?> expreturntypeclass) {
		this.expreturntypeclass = expreturntypeclass;
	}
	public List<String> getExpargstypes() {
		return expargstypes;
	}
	public void setExpargstypes(LinkedList<String> expargstypes) {
		this.expargstypes = expargstypes;
	}
	public List<Class<?>> getExpargstypesclasses() {
		return expargstypesclasses;
	}
	public void setExpargstypesclasses(LinkedList<Class<?>> expargstypesclasses) {
		this.expargstypesclasses = expargstypesclasses;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException {
		TypeCheck o = (TypeCheck)super.clone();
		if (expargstypes != null)
		{
			o.expargstypes = (LinkedList<String>)expargstypes.clone();
		}
		if (expargstypesclasses != null)
		{
			o.expargstypesclasses = (LinkedList<Class<?>>)expargstypesclasses.clone();
		}
		return o;
	}
	
}