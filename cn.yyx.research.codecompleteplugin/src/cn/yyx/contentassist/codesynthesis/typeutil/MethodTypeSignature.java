package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.List;

public class MethodTypeSignature {
	
	private Class<?> returntype = null;
	private List<Class<?>> argtypes = null;
	
	public MethodTypeSignature(Class<?> returntype, List<Class<?>> argtypes) {
		this.setReturntype(returntype);
		this.setArgtypes(argtypes);
	}

	public Class<?> getReturntype() {
		return returntype;
	}

	public void setReturntype(Class<?> returntype) {
		this.returntype = returntype;
	}

	public List<Class<?>> getArgtypes() {
		return argtypes;
	}

	public void setArgtypes(List<Class<?>> argtypes) {
		this.argtypes = argtypes;
	}
	
}