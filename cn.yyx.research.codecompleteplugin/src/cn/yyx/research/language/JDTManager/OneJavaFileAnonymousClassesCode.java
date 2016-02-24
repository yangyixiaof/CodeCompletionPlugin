package cn.yyx.research.language.JDTManager;

import java.util.ArrayList;

import cn.yyx.research.language.simplified.JDTManager.JavaCode;

public class OneJavaFileAnonymousClassesCode extends JavaCode {
	
	String mwcontent = null;
	ArrayList<String> codelist = null;
	
	public OneJavaFileAnonymousClassesCode() {
	}
	
	public void AddPreDeclrations(MethodWindow mw)
	{
		mwcontent = mw.toString();
		codelist = mw.toList();
	}
	
	public void AddOneMethodNodeCode(NodeCode nc)
	{
		if (mwcontent != null)
		{
			sb.append(mwcontent);
			codes.addAll(codelist);
		}
		AddOneNodeCode(nc);
	}
	
	public void OneSentenceEnd()
	{
		/*System.err.println(sb);
		if (sb.length() == 0 || sb.charAt(sb.length()-1) != '.')
		{
			sb.append(".");
		}*/
	}
	
}