package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public abstract class TypeComputationKind {
	
	protected CCType pre = null;
	protected CCType post = null;
	
	public abstract void HandlePre(CCType pre) throws TypeConflictException;
	public abstract void HandlePost(CCType post) throws TypeConflictException;
	public abstract CCType HandleResult() throws TypeConflictException;
	
	public boolean HandleOver() throws TypeConflictException {
		if (pre != null && post != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean PreIsHandled() throws TypeConflictException {
		if (pre != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean PostIsHandled() throws TypeConflictException {
		if (post != null)
		{
			return true;
		}
		return false;
	}
	
}