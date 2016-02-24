package cn.yyx.contentassist.specification;

import java.util.ArrayList;

import org.eclipse.jdt.internal.ui.text.java.ParameterGuessingProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

@SuppressWarnings("restriction")
public class MethodMember {
	
	String name = null;
	String returntype = null;
	String whereDeclared = null;
	ArrayList<String> argtypelist = new ArrayList<String>();
	ArrayList<String> argnamelist = new ArrayList<String>();
	
	public MethodMember(ICompletionProposal icp) {
		initial(icp.getDisplayString());
	}
	
	public MethodMember(ParameterGuessingProposal proposal) {
		initial(proposal.getDisplayString());
	}
	
	private void initial(String pstr)
	{
		String[] strs = pstr.split(":|-");
		String[] funs = strs[0].trim().split("\\(|\\)|,");
		name = funs[0].trim();
		int flen = funs.length;
		for (int i=1;i<flen;i++)
		{
			String arg = funs[i].trim();
			// System.out.println("arg:" + arg + ",pstr:"+pstr + ",funs:" + strs[0] + "#");
			String[] as = arg.split(" ");
			argtypelist.add(as[0]);
			argnamelist.add(as[1]);
		}
		returntype = strs[1].trim();
		if (strs.length == 3)
		{
			whereDeclared = strs[2].trim();
		}
	}
	
	@Override
	public String toString() {
		String args = "";
		int len = argtypelist.size();
		for (int i=0;i<len;i++)
		{
			String type = argtypelist.get(i);
			String name = argnamelist.get(i);
			args += "#" + type + "$" + name + "#";
		}
		return returntype + " " + name + args + " - " + whereDeclared;
	}
	
}