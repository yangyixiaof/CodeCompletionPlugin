package cn.yyx.contentassist.commonutils;

import java.util.List;

public class AdditionalInfo {
	
	private String methodName = null;
	private List<String> possibleArgType = null;
	private String methodReturnType = null;
	
	private String directlyMemberHint = null;
	private boolean directlyMemberIsMethod = false;
	private String directlyMemberType = null;
	private String ModifiedMember = null;
	
	public AdditionalInfo() {
		
	}
	
	public List<String> getPossibleArgType() {
		return possibleArgType;
	}

	public void setPossibleArgType(List<String> possibleArgType) {
		this.possibleArgType = possibleArgType;
	}

	public String getMethodReturnType() {
		return methodReturnType;
	}

	public void setMethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
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

	public String getDirectlyMemberType() {
		return directlyMemberType;
	}

	public void setDirectlyMemberType(String directlyMemberType) {
		this.directlyMemberType = directlyMemberType;
	}

	public String getModifiedMember() {
		return ModifiedMember;
	}

	public void setModifiedMember(String modifiedMember) {
		ModifiedMember = modifiedMember;
	}
	
}