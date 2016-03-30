package cn.yyx.contentassist.specification;

import java.util.LinkedList;

import org.eclipse.jdt.internal.ui.text.java.ParameterGuessingProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

@SuppressWarnings("restriction")
public class MethodMember {
	
	private String name = null;
	private String returntype = null;
	private String whereDeclared = null;
	private LinkedList<String> argtypelist = new LinkedList<String>();
	private LinkedList<String> argnamelist = new LinkedList<String>();
	
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
		setName(funs[0].trim());
		int flen = funs.length;
		for (int i=1;i<flen;i++)
		{
			String arg = funs[i].trim();
			// System.out.println("arg:" + arg + ",pstr:"+pstr + ",funs:" + strs[0] + "#");
			String[] as = arg.split(" ");
			getArgtypelist().add(as[0]);
			getArgnamelist().add(as[1]);
		}
		setReturntype(strs[1].trim());
		if (strs.length == 3)
		{
			setWhereDeclared(strs[2].trim());
		}
	}
	
	@Override
	public String toString() {
		String args = "";
		int len = getArgtypelist().size();
		for (int i=0;i<len;i++)
		{
			String type = getArgtypelist().get(i);
			String name = getArgnamelist().get(i);
			args += "#" + type + "$" + name + "#";
		}
		return getReturntype() + " " + getName() + args + " - " + getWhereDeclared();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturntype() {
		return returntype;
	}

	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}

	public String getWhereDeclared() {
		return whereDeclared;
	}

	public void setWhereDeclared(String whereDeclared) {
		this.whereDeclared = whereDeclared;
	}

	public LinkedList<String> getArgtypelist() {
		return argtypelist;
	}

	public void setArgtypelist(LinkedList<String> argtypelist) {
		this.argtypelist = argtypelist;
	}

	public LinkedList<String> getArgnamelist() {
		return argnamelist;
	}

	public void setArgnamelist(LinkedList<String> argnamelist) {
		this.argnamelist = argnamelist;
	}
	
}