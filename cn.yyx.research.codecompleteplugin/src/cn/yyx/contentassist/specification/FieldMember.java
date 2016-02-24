package cn.yyx.contentassist.specification;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class FieldMember {
	
	String name = null;
	String type = null;
	String whereDeclared = null;
	
	public FieldMember(ICompletionProposal icp) {
		String pstr =  icp.getDisplayString().trim();
		String[] strs = pstr.split(":|-");
		name = strs[0].trim();
		type = strs[1].trim();
		if (strs.length == 3)
		{
			whereDeclared = strs[2].trim();
		}
	}
	
	@Override
	public String toString() {
		return name + "#" + type + "#" + whereDeclared;
	}
	
}