package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListDynamicHeper<T> {
	
	public List<T> ReverseList(List<T> ls)
	{
		List<T> result = new LinkedList<T>();
		Iterator<T> itr = ls.iterator();
		while (itr.hasNext())
		{
			T t = itr.next();
			result.add(0, t);
		}
		return result;
	}
	
}