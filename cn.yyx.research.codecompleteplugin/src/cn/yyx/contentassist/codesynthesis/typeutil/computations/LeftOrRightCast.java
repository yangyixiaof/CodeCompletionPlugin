package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class LeftOrRightCast extends TypeComputationKind {

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
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeCheckHelper.CanBeMutualCast(getPre(), getPost()))
		{
			return getPre();
		}
		throw new TypeConflictException("two types can not be casted.");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		LeftOrRightCast brtssnb = new LeftOrRightCast();
		brtssnb.setPost((CCType) post.clone());
		brtssnb.setPre((CCType) pre.clone());
		return brtssnb;
	}
	
}