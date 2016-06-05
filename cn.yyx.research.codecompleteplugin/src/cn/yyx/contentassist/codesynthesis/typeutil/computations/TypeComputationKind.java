package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public abstract class TypeComputationKind implements Cloneable {
	
	protected CCType pre = null;
	protected CCType post = null;
	
	public abstract void HandlePre(CCType pre) throws TypeConflictException;
	public abstract void HandlePost(CCType post) throws TypeConflictException;
	public abstract CCType HandleResult() throws TypeConflictException;
	
	@Override
	public abstract Object clone() throws CloneNotSupportedException;
	
	public void ClearPost()
	{
		setPost(null);
	}
	
	public boolean HandleOver() throws TypeConflictException {
		if (getPre() != null && getPost() != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean PreIsHandled() throws TypeConflictException {
		if (getPre() != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean PostIsHandled() throws TypeConflictException {
		if (getPost() != null)
		{
			return true;
		}
		return false;
	}
	
	public CCType getPost() {
		return post;
	}
	
	public void setPost(CCType post) {
		this.post = post;
	}
	
	public CCType getPre() {
		return pre;
	}
	
	public void setPre(CCType pre) {
		this.pre = pre;
	}
	
}