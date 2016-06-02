package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class BooleanRTwoSideSame extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
	}
	
	@Override
	public boolean HandleOver() throws TypeConflictException {
		if (pre != null && post != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (pre instanceof InferredCCType || post instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.CCTypeSame(pre, post))
		{
			return new CCType(boolean.class, "boolean");
		}
		throw new TypeConflictException("pre and post are not same in BooleanRTwoSideSame.");
	}
	
}
