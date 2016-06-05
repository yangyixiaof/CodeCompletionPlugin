package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class NumberBitRTwoSideSameNumberBit extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()) && !(pre instanceof InferredCCType))
			{
				throw new TypeConflictException("left of NumberBitRTwoSideSameNumberBit is not number bit. left is:" + pre);
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
				throw new TypeConflictException("right of NumberBitRTwoSideSameNumberBit is not number bit. right is:" + post);
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		return getPost();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		NumberBitRTwoSideSameNumberBit tcmp = new NumberBitRTwoSideSameNumberBit();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}
	
}