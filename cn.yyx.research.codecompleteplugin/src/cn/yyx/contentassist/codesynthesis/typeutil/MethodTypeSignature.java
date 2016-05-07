package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.specification.MethodMember;

public class MethodTypeSignature {
	
	private List<CCType> returntype = null;
	private List<LinkedList<CCType>> argtypes = null;
	
	// private Class<?> returntype = null;
	// private List<Class<?>> argtypes = null;
	
	public static MethodTypeSignature GenerateMethodTypeSignature(MethodMember mm, JavaContentAssistInvocationContext javacontext)
	{
		if (mm != null)
		{
			LinkedList<CCType> rt = TypeResolver.ResolveType(mm.getReturntype(), javacontext);
			List<LinkedList<CCType>> arts = TypeResolver.ResolveType(mm.getArgtypelist(), javacontext);
			return new MethodTypeSignature(rt, arts);
		}
		return null;
	}
	
	public MethodTypeSignature(List<CCType> returntype, List<LinkedList<CCType>> argtypes) {
		this.setReturntype(returntype);
		this.setArgtypes(argtypes);
	}

	public List<CCType> getReturntype() {
		return returntype;
	}

	public void setReturntype(List<CCType> returntype) {
		this.returntype = returntype;
	}

	public List<LinkedList<CCType>> getArgtypes() {
		return argtypes;
	}

	public void setArgtypes(List<LinkedList<CCType>> argtypes) {
		this.argtypes = argtypes;
	}
	
}