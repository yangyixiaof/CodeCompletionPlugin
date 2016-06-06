package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class NumberOpAssign extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()) && !(pre instanceof InferredCCType))
			{
				throw new TypeConflictException("left of NumberOpAssign is not number bit or string. left is:" + pre);
			}
		}
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.setPost(post);
		if (post != null)
		{
			if (!TypeComputer.IsNumberBit(post.getCls()) && !(post instanceof InferredCCType))
			{
				throw new TypeConflictException("right of NumberOpAssign is not number bit or string. post is:" + post);
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (getPre() == null || getPost() == null)
		{
			throw new TypeConflictException("pre:" + pre + " and post:" + post + " are not same in InheritLeftOrRightTwoSameSide.");
		}
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.IsStrictNumberBit(getPre().getCls()) && TypeComputer.IsStrictNumberBit(getPost().getCls()))
		{
			// TODO do number type compute.
			return getPre();
		}
		/*if (TypeComputer.CCTypeSame(getPre(), getPost()))
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
		}*/
		throw new TypeConflictException("pre:" + pre + " and post:" + post + " are not same in InheritLeftOrRightTwoSameSide.");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		NumberOpAssign tcmp = new NumberOpAssign();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}
}