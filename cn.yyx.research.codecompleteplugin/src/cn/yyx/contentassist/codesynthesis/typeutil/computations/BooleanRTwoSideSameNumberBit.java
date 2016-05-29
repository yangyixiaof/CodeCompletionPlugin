package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class BooleanRTwoSideSameNumberBit extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (!TypeComputer.IsStrictNumberBit(pre.getCls()))
			{
				throw new TypeConflictException("left of BooleanRTwoSideSameNumberBit not number bit.");
			}
		}
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (!TypeComputer.IsStrictNumberBit(post.getCls()))
			{
				throw new TypeConflictException("right of inBooleanRTwoSideSameNumberBitherit not number bit.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return new CCType(boolean.class, "boolean");
	}
	
}