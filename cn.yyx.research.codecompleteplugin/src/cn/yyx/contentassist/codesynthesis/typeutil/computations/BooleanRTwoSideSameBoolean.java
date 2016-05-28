package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class BooleanRTwoSideSameBoolean extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (pre.getCls() != boolean.class)
			{
				throw new TypeConflictException("type is not boolean.");
			}
		}
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (post.getCls() != boolean.class)
			{
				throw new TypeConflictException("type is not boolean.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return new CCType(boolean.class, "boolean");
	}

}