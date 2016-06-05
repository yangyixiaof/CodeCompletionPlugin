package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class InheritLeftOrRightTwoSameSide extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.setPost(post);
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (getPre() == null || getPost() == null)
		{
			throw new TypeConflictException("pre and post are null in InheritLeftOrRightTwoSameSide.");
		}
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.CCTypeSame(getPre(), getPost()))
		{
			if (getPost().getCls() != null)
			{
				return getPost();
			}
			if (getPre().getCls() != null)
			{
				return getPre();
			}
			return getPre();
		}
		throw new TypeConflictException("pre and post are not same in InheritLeftOrRightTwoSameSide.");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		InheritLeftOrRightTwoSameSide brtssnb = new InheritLeftOrRightTwoSameSide();
		brtssnb.setPost((CCType) post.clone());
		brtssnb.setPre((CCType) pre.clone());
		return brtssnb;
	}
}