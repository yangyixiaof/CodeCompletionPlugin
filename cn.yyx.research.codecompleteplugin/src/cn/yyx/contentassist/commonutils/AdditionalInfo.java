package cn.yyx.contentassist.commonutils;

import java.util.List;

public class AdditionalInfo {
	
	private boolean firstInvokerInArgList;
	private String methodName = null;
	private List<String> possibleArgType = null;
	private String methodReturnType = null;
	
	private String directlyMemberHint = null;
	
	public AdditionalInfo() {
		
	}
	
	public boolean isFirstInvokerInArgList() {
		return firstInvokerInArgList;
	}
	
	public void setFirstInvokerInArgList(boolean firstInvokerInArgList) {
		this.firstInvokerInArgList = firstInvokerInArgList;
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
	
}