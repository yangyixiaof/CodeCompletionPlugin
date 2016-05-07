package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.specification.MethodMember;

public class MethodTypeSignature {
	
	private CCType returntype = null;
	private List<CCType> argtypes = null;
	
	// private Class<?> returntype = null;
	// private List<Class<?>> argtypes = null;
	
	public static MethodTypeSignature GenerateMethodTypeSignature(MethodMember mm, JavaContentAssistInvocationContext javacontext)
	{
		if (mm != null)
		{
			CCType rt = TypeResolver.ResolveType(mm.getReturntype(), javacontext);
			List<CCType> arts = TypeResolver.ResolveType(mm.getArgtypelist(), javacontext);
			return new MethodTypeSignature(rt, arts);
		}
		return null;
	}
	
	public MethodTypeSignature(CCType returntype, List<CCType> argtypes) {
		this.setReturntype(returntype);
		this.setArgtypes(argtypes);
	}

	public CCType getReturntype() {
		return returntype;
	}

	public void setReturntype(CCType returntype) {
		this.returntype = returntype;
	}

	public List<CCType> getArgtypes() {
		return argtypes;
	}

	public void setArgtypes(List<CCType> argtypes) {
		this.argtypes = argtypes;
	}
	
}