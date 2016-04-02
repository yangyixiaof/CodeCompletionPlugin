package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.Sentence;

public class ListHelper {
	
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
	
	public static String ConcatJoinLast(int size, List<Sentence> analysislist) {
		StringBuffer sb = new StringBuffer("");
		int end = analysislist.size() - 1;
		int start = end + 1 - size;
		Iterator<Sentence> itr = analysislist.iterator();
		for (int i=0;i<start;i++)
		{
			itr.next();
		}
		for (int i = start; i <= end; i++) {
			Sentence sete = itr.next();
			String split = " ";
			if (i == end) {
				split = "";
			}
			sb.append(sete.getSentence() + split);
		}
		return sb.toString().trim();
	}
	
}