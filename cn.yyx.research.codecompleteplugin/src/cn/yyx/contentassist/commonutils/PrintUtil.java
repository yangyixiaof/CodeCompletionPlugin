package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;

public class PrintUtil {
	
	public static void PrintList(List<String> list, String kindinfo)
	{
		if (CodeCompletionMetaInfo.DebugMode)
		{
			System.out.println(kindinfo + " begin:");
			Iterator<String> itr = list.iterator();
			while (itr.hasNext())
			{
				String scnt = itr.next();
				System.out.println(scnt);
			}
			System.out.println(kindinfo + " end.");
		}
	}
	
}