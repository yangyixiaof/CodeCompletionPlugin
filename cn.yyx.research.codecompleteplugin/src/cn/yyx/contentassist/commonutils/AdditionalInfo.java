package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;
import java.util.List;

public class AdditionalInfo {
	
	private String methodName = null;
	
	private List<TypeCheck> tcs = new LinkedList<TypeCheck>();
	
	private String directlyMemberHint = null;
	private boolean directlyMemberIsMethod = false;
	private String ModifiedMember = null;
	
	public AdditionalInfo() {
		
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDirectlyMemberHint() {
		return directlyMemberHint;
	}

	public void setDirectlyMemberHint(String directlyMemberHint) {
		this.directlyMemberHint = directlyMemberHint;
	}

	public boolean isDirectlyMemberIsMethod() {
		return directlyMemberIsMethod;
	}

	public void setDirectlyMemberIsMethod(boolean directlyMemberIsMethod) {
		this.directlyMemberIsMethod = directlyMemberIsMethod;
	}

	public String getModifiedMember() {
		return ModifiedMember;
	}

	public void setModifiedMember(String modifiedMember) {
		ModifiedMember = modifiedMember;
	}
	
	public void AddTypeCheck(TypeCheck tc)
	{
		tcs.add(tc);
	}

	public List<TypeCheck> getTcs() {
		return tcs;
	}

	public void setTcs(List<TypeCheck> tcs) {
		this.tcs = tcs;
	}
	
}