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
				throw new TypeConflictException("left of NumberBitRTwoSideSameNumberBit is not number bit.");
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
				throw new TypeConflictException("right of NumberBitRTwoSideSameNumberBit is not number bit.");
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
		NumberBitRTwoSideSameNumberBit brtssnb = new NumberBitRTwoSideSameNumberBit();
		brtssnb.setPost((CCType) post.clone());
		brtssnb.setPre((CCType) pre.clone());
		return brtssnb;
	}
	
}