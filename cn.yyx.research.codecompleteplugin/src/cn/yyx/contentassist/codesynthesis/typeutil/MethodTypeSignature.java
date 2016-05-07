package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
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
	

	public static MethodTypeSignature TranslateMethodMember(MethodMember mm, JavaContentAssistInvocationContext javacontext)
	{
		String rttype = mm.getReturntype();
		// tc.setExpreturntype(rttype);
		LinkedList<CCType> c = TypeResolver.ResolveType(rttype, javacontext);
		// tc.setExpreturntypeclass(c);
		LinkedList<String> tplist = mm.getArgtypelist();
		// tc.setExpargstypes(tplist);
		List<LinkedList<CCType>> tpclist = new LinkedList<LinkedList<CCType>>();
		Iterator<String> itr = tplist.iterator();
		while (itr.hasNext())
		{
			String tp = itr.next();
			LinkedList<CCType> tpc = TypeResolver.ResolveType(tp, javacontext);
			tpclist.add(tpc);
		}
		// tc.setExpargstypesclasses(tpclist);
		MethodTypeSignature tc = new MethodTypeSignature(c, tpclist);
		return tc;
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