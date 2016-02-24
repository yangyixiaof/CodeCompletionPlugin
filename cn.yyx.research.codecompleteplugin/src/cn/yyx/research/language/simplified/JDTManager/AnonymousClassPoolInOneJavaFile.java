package cn.yyx.research.language.simplified.JDTManager;

import java.util.ArrayList;
import java.util.Iterator;

import cn.yyx.research.language.JDTManager.MethodWindow;
import cn.yyx.research.language.JDTManager.OneJavaFileAnonymousClassesCode;

public class AnonymousClassPoolInOneJavaFile {
		
	OneJavaFileAnonymousClassesCode recently = null;
	
	int anonymmousLevel = -1;
	
	ArrayList<ArrayList<OneJavaFileAnonymousClassesCode>> ojfacclist = new ArrayList<ArrayList<OneJavaFileAnonymousClassesCode>>();
	
	public OneJavaFileAnonymousClassesCode EnterAnonymousClass(MethodWindow mw)
	{
		anonymmousLevel++;
		int osize = ojfacclist.size();
		if (anonymmousLevel >= osize)
		{
			int addsize = anonymmousLevel - osize + 1;
			for (int i=0;i<addsize;i++)
			{
				ojfacclist.add(new ArrayList<OneJavaFileAnonymousClassesCode>());
			}
		}
		OneJavaFileAnonymousClassesCode nowanonymous = new OneJavaFileAnonymousClassesCode();
		nowanonymous.AddPreDeclrations(mw);
		recently = nowanonymous;
		ojfacclist.get(anonymmousLevel).add(nowanonymous);
		return nowanonymous;
	}
	
	public void ExitAnonymousClass()
	{
		
	}

	public boolean IsEmpty() {
		return ojfacclist.size() == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		Iterator<ArrayList<OneJavaFileAnonymousClassesCode>> itr2 = ojfacclist.iterator();
		while (itr2.hasNext())
		{
			ArrayList<OneJavaFileAnonymousClassesCode> jfs = itr2.next();
			Iterator<OneJavaFileAnonymousClassesCode> itr = jfs.iterator();
			while (itr.hasNext())
			{
				OneJavaFileAnonymousClassesCode ojf = itr.next();
				sb.append(ojf.toString());
			}
		}
		return sb.toString();
	}

	public ArrayList<String> GetRecentAnalyseList() {
		return recently.toList();
	}
	
}