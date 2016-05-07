package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;

public class TypeCheckHelper {
	
	/*public static boolean CanBeMutualCast(TypeCheck one, TypeCheck two)
	{
		Class<?> onetype = one.getExpreturntypeclass();
		Class<?> twotype = two.getExpreturntypeclass();
		return CanBeMutualCast(onetype, twotype);
	}*/
	
	public static boolean CanBeMutualCast(LinkedList<CCType> cs, CCType rtclass) {
		Iterator<CCType> itr = cs.iterator();
		while (itr.hasNext())
		{
			CCType cct = itr.next();
			if (CanBeMutualCast(cct, rtclass))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean CanBeMutualCast(CCType onetype, CCType twotype)
	{
		if (onetype == null || twotype == null)
		{
			return true;
		}
		Class<?> ot = NormalizeClass(onetype.getCls());
		Class<?> tt = NormalizeClass(twotype.getCls());
		if (ot.isAssignableFrom(tt) || tt.isAssignableFrom(ot))
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
	
}