package cn.yyx.contentassist.specification;

import org.eclipse.jdt.internal.ui.text.java.JavaCompletionProposal;

@SuppressWarnings("restriction")
public class FieldMember {
	
	private String name = null;
	private String type = null;
	private String whereDeclared = null;
	
	public FieldMember(JavaCompletionProposal icp) {
		String pstr =  icp.getDisplayString().trim();
		String[] strs = pstr.split(":|-");
		setName(strs[0].trim());
		setType(strs[1].trim());
		if (strs.length == 3)
		{
			setWhereDeclared(strs[2].trim());
		}
	}
	
	@Override
	public String toString() {
		return getName() + "#" + getType() + "#" + getWhereDeclared();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWhereDeclared() {
		return whereDeclared;
	}

	public void setWhereDeclared(String whereDeclared) {
		this.whereDeclared = whereDeclared;
	}
	
}