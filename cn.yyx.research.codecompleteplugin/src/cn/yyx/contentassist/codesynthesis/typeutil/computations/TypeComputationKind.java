package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public abstract class TypeComputationKind {
	
	public abstract void HandlePre(CCType pre) throws TypeConflictException;
	public abstract boolean PreIsHandled(CCType pre) throws TypeConflictException;
	public abstract void HandlePost(CCType post) throws TypeConflictException;
	public abstract boolean PostIsHandled(CCType pre) throws TypeConflictException;
	public abstract boolean HandleOver() throws TypeConflictException;
	public abstract CCType HandleResult() throws TypeConflictException;
	
}