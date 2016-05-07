package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.specification.MethodMember;

public class TypeCheckHelper {
	
	/*public static boolean CanBeMutualCast(TypeCheck one, TypeCheck two)
	{
		Class<?> onetype = one.getExpreturntypeclass();
		Class<?> twotype = two.getExpreturntypeclass();
		return CanBeMutualCast(onetype, twotype);
	}*/
	
	public static boolean CanBeMutualCast(CCType onetype, CCType twotype)
	{
		if (onetype == null || twotype == null)
		{
			return true;
		}
		onetype = NormalizeClass(onetype);
		twotype = NormalizeClass(twotype);
		if (onetype.isAssignableFrom(twotype) || twotype.isAssignableFrom(onetype))
		{
			return true;
		}
		return false;
	}
	
	private static Class<?> NormalizeClass(Class<?> onetype)
	{
		if (onetype.equals(int.class))
		{
			onetype = Integer.class;
		}
		if (onetype.equals(boolean.class))
		{
			onetype = Boolean.class;
		}
		if (onetype.equals(float.class))
		{
			onetype = Float.class;
		}
		if (onetype.equals(double.class))
		{
			onetype = Double.class;
		}
		if (onetype.equals(char.class))
		{
			onetype = Character.class;
		}
		return onetype;
	}
	
	public static MethodTypeSignature TranslateMethodMember(MethodMember mm, JavaContentAssistInvocationContext javacontext)
	{
		String rttype = mm.getReturntype();
		// tc.setExpreturntype(rttype);
		Class<?> c = TypeResolver.ResolveType(rttype, javacontext);
		// tc.setExpreturntypeclass(c);
		LinkedList<String> tplist = mm.getArgtypelist();
		// tc.setExpargstypes(tplist);
		LinkedList<Class<?>> tpclist = new LinkedList<Class<?>>();
		Iterator<String> itr = tplist.iterator();
		while (itr.hasNext())
		{
			String tp = itr.next();
			Class<?> tpc = TypeResolver.ResolveType(tp, javacontext);
			tpclist.add(tpc);
		}
		// tc.setExpargstypesclasses(tpclist);
		MethodTypeSignature tc = new MethodTypeSignature(c, tpclist);
		return tc;
	}
	
}