package cn.yyx.contentassist.test;

import java.util.LinkedList;
import java.util.List;

public class EspeciallyDesignedListTypeTest {
	
	public void haha()
	{
		List<String> somelist = GetList();
		for (String li : somelist)
		{
			System.out.println(li);
		}
	}
	
	public List<String> GetList()
	{
		return new LinkedList<String>();
	}
	
}
