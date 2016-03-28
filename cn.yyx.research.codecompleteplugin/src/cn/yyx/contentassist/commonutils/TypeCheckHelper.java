package cn.yyx.contentassist.commonutils;

public class TypeCheckHelper {
	
	public static boolean CanBeMutualCast(TypeCheck one, TypeCheck two)
	{
		Class<?> onetype = one.getExpreturntypeclass();
		Class<?> twotype = two.getExpreturntypeclass();
		return CanBeMutualCast(onetype, twotype);
	}
	
	public static boolean CanBeMutualCast(Class<?> onetype, Class<?> twotype)
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
	
}