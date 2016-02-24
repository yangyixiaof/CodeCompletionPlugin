package cn.yyx.research.language.JDTManager;

import cn.yyx.research.language.simplified.JDTManager.JavaCode;

public class OneJavaFileCode extends JavaCode{
	
	StringBuilder sb = new StringBuilder("");
	
	public OneJavaFileCode() {
	}
	
	public void AddOneMethodNodeCode(NodeCode nc)
	{
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