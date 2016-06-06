package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class PlusAssign extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()) && pre.getCls() != String.class && !(pre instanceof InferredCCType))
			{
				throw new TypeConflictException("left of PlusAssign is not number bit or string. left is:" + pre);
			}
		}
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.setPost(post);
		if (post != null)
		{
			if (!TypeComputer.IsNumberBit(post.getCls()) && post.getCls() != String.class && !(post instanceof InferredCCType))
			{
				throw new TypeConflictException("right of PlusAssign is not number bit or string. post is:" + post);
			}
		}
	}

	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.IsStrictNumberBit(getPre().getCls()) && TypeComputer.IsStrictNumberBit(getPost().getCls()))
		{
			// TODO do number type compute.
			return getPre();
		}
		if (getPre().getCls() == String.class)
		{
			return new CCType(String.class, "String");
		}
		throw new TypeConflictException("Plus Assign two types not right.");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PlusAssign tcmp = new PlusAssign();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}

}
