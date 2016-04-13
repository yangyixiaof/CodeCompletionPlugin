package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.specification.MethodMember;

public class MethodTypeSignature {
	
	private Class<?> returntype = null;
	private List<Class<?>> argtypes = null;
	
	public static MethodTypeSignature GenerateMethodTypeSignature(MethodMember mm, JavaContentAssistInvocationContext javacontext)
	{
		if (mm != null)
		{
			Class<?> rt = TypeResolver.ResolveType(mm.getReturntype(), javacontext);
			List<Class<?>> arts = TypeResolver.ResolveType(mm.getArgtypelist(), javacontext);
			return new MethodTypeSignature(rt, arts);
		}
		return null;
	}
	
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