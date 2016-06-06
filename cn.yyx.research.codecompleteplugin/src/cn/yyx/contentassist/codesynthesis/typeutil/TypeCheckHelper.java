package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.List;

import cn.yyx.research.language.JDTManager.GCodeMetaInfo;

public class TypeCheckHelper {
	
	/*public static boolean CanBeMutualCast(TypeCheck one, TypeCheck two)
	{
		Class<?> onetype = one.getExpreturntypeclass();
		Class<?> twotype = two.getExpreturntypeclass();
		return CanBeMutualCast(onetype, twotype);
	}*/
	
	public static boolean IsInferredType(CCType cct)
	{
		if (cct instanceof InferredCCType)
		{
			return true;
		}
		return false;
	}
	
	public static boolean IsInferredType(String it)
	{
		if (it.equals(GCodeMetaInfo.InferedType))
		{
			return true;
		}
		return false;
	}
	
	public static boolean CanBeMutualCast(List<CCType> cs, CCType rtclass) {
		if (cs == null)
		{
			if (rtclass == null || rtclass.getCls() == null)
			{
				return true;
			}
			return false;
		}
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
		if (onetype instanceof InferredCCType || twotype instanceof InferredCCType)
		{
			return true;
		}
		if (onetype.getCls() != null && twotype.getCls() != null)
		{
			if (onetype.getCls().isArray() && twotype.getCls().isArray())
			{
				if (onetype.getCls().getComponentType().isAssignableFrom(twotype.getCls().getComponentType()))
				{
					return true;
				}
				if (twotype.getCls().getComponentType().isAssignableFrom(onetype.getCls().getComponentType()))
				{
					return true;
				}
				if (NormalizeClass(twotype.getCls().getComponentType()) == NormalizeClass(onetype.getCls().getComponentType()))
				{
					return true;
				}
			}
		}
		Class<?> ot = NormalizeClass(onetype.getCls());
		Class<?> tt = NormalizeClass(twotype.getCls());
		if (onetype instanceof VarArgCCType)
		{
			ot = NormalizeClass(((VarArgCCType) onetype).getCompocls());
		}
		if (twotype instanceof VarArgCCType)
		{
			tt = NormalizeClass(((VarArgCCType) twotype).getCompocls());
		}
		if (ot == null || tt == null)
		{
			return true;
		}
		if (TypeComputer.IsStrictNumberBit(ot) && TypeComputer.IsStrictNumberBit(tt))
		{
			return true;
		}
		if (ot.isAssignableFrom(tt) || tt.isAssignableFrom(ot))
		{
			return true;
		}
		return false;
	}
	
	public static Class<?> NormalizeClass(Class<?> onetype)
	{
		if (onetype == null)
		{
			return null;
		}
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