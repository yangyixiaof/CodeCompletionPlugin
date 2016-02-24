package cn.yyx.research.language.Utility;

import java.util.ArrayList;

import cn.yyx.research.language.JDTManager.EnteredScopeStack;
import cn.yyx.research.language.JDTManager.OneScope;

public class TestUtil {
	
	public static void PrintEnteredScopeStack(EnteredScopeStack stack)
	{
		ArrayList<OneScope> ps = stack.getStack();
		int len = ps.size();
		System.out.println("begin");
		for (int i=0;i<len;i++)
		{
			OneScope os= ps.get(i);
			System.out.println(os.getID());
		}
		System.out.println("end");
	}
	
}
