package cn.yyx.contentassist.commonutils;

import java.util.LinkedList;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;

public class ASTOffsetInfo {
	
	private boolean isInAnonymousClass = false;
	private boolean isInFieldLevel = false;
	private String indent = null;
	private String methodDeclarationReturnType = null;
	private LinkedList<CCType> methodReturnResolvedType = null;
	private boolean methodReturnResolved = false;
	
	public ASTOffsetInfo() {
	}

	public boolean isInAnonymousClass() {
		return isInAnonymousClass;
	}

	public void setInAnonymousClass(boolean isInAnonymousClass) {
		this.isInAnonymousClass = isInAnonymousClass;
	}

	public boolean isInFieldLevel() {
		return isInFieldLevel;
	}

	public void setInFieldLevel(boolean isInFieldLevel) {
		this.isInFieldLevel = isInFieldLevel;
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}
	
	@Override
	public String toString() {
		return "isInAnonymousClass:" + isInAnonymousClass + ";isInFieldLevel:" + isInFieldLevel + ";indent:" + indent;
	}

	public String getMethodDeclarationReturnType() {
		return methodDeclarationReturnType;
	}

	public void setMethodDeclarationReturnType(String methodDeclarationReturnType) {
		this.methodDeclarationReturnType = methodDeclarationReturnType;
	}

	public LinkedList<CCType> getMethodReturnResolvedType() {
		return methodReturnResolvedType;
	}

	public void setMethodReturnResolvedType(LinkedList<CCType> methodReturnResolvedType) {
		this.methodReturnResolvedType = methodReturnResolvedType;
	}

	public boolean isMethodReturnResolved() {
		return methodReturnResolved;
	}

	public void setMethodReturnResolved(boolean methodReturnResolved) {
		this.methodReturnResolved = methodReturnResolved;
	}
	
}