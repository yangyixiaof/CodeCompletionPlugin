package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;

public class ArrayUtil {
	
	public static ArrayList<Boolean> InitialBooleanArray(int size)
	{
		ArrayList<Boolean> result = new ArrayList<Boolean>();
		for (int i=0;i<size;i++)
		{
			result.add(false);
		}
		return result;
	}
	
}
