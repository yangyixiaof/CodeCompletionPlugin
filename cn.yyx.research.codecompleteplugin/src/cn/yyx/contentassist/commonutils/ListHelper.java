package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;
import java.util.Iterator;

public class ListHelper {
	
	public ListHelper() {
		
	}
	
	public static void PrintList(ArrayList<String> result)
	{
		System.out.println("Element Begin:");
		Iterator<String> itr = result.iterator();
		while (itr.hasNext())
		{
			String ele = itr.next();
			System.out.println("Element:" + ele);
		}
		System.out.println("Element End.");
	}
	
}
