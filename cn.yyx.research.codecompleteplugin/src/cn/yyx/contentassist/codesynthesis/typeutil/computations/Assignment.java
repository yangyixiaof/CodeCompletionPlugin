package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.SameTypeConflictException;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class Assignment extends TypeComputationKind {

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
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (TypeComputer.CCTypeSame(getPre(), getPost()))
		{
			return pre;
		}
		if (TypeComputer.IsStrictNumberBit(getPre().getCls()) && TypeComputer.IsStrictNumberBit(getPost().getCls()))
		{
			// TODO do number type compute.
			return getPre();
		}
		throw new SameTypeConflictException("assign type check error. pre:" + pre + ";post:" + post, post);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Assignment tcmp = new Assignment();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}

}
