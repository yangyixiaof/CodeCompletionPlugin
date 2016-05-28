package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class LeftOrRightCast extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (TypeCheckHelper.CanBeMutualCast(pre, post))
		{
			return pre;
		}
		throw new TypeConflictException("two types can not be casted.");
	}
	
}