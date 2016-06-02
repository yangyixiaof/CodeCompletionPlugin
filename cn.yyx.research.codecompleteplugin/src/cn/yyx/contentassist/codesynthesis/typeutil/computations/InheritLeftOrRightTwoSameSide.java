package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class InheritLeftOrRightTwoSameSide extends TypeComputationKind {

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
		if (pre == null || post == null)
		{
			throw new TypeConflictException("pre and post are null in InheritLeftOrRightTwoSameSide.");
		}
		if (pre instanceof InferredCCType || post instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.CCTypeSame(pre, post))
		{
			if (post.getCls() != null)
			{
				return post;
			}
			if (pre.getCls() != null)
			{
				return pre;
			}
			return pre;
		}
		throw new TypeConflictException("pre and post are not same in InheritLeftOrRightTwoSameSide.");
	}
}
