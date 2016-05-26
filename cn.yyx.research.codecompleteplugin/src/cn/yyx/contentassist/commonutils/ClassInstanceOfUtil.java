package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codeutils.SWrapper;
import cn.yyx.contentassist.codeutils.statement;

public class ClassInstanceOfUtil {
	
	public static boolean ObjectInstanceOf(Object ref, Class<?> cls)
	{
		if (cls.isAssignableFrom(ref.getClass()))
		{
			return true;
		}
		if (isWrapperStatement(ref))
		{
			statement smt = ((SWrapper)ref).GetContent();
			if (cls.isAssignableFrom(smt.getClass()))
			{
				return true;
			}
		}
		if (ref instanceof CSFlowLineData)
		{
			return ((CSFlowLineData) ref).HasSpecialProperty(cls);
		}
		return false;
	}
	
	private static boolean isWrapperStatement(Object ref)
	{
		if (ref instanceof SWrapper)
		{
			return true;
		}
		return false;
	}
	
}