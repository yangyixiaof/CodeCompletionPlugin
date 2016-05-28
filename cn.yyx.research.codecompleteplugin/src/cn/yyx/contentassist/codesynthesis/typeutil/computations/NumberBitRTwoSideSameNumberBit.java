package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class NumberBitRTwoSideSameNumberBit extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()))
			{
				throw new TypeConflictException("left of NumberBitRTwoSideSameNumberBit is not number bit.");
			}
		}
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (!TypeComputer.IsNumberBit(post.getCls()))
			{
				throw new TypeConflictException("right of NumberBitRTwoSideSameNumberBit is not number bit.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return post;
	}
	
}