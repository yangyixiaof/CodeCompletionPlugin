package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class StringNumberBitRTwoSideSameNumberBitOrOneString extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()) && pre.getCls() != String.class)
			{
				throw new TypeConflictException("left of StringNumberBitRTwoSideSameNumberBitOrOneString is not number bit or string.");
			}
		}
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (!TypeComputer.IsNumberBit(post.getCls()) && post.getCls() != String.class)
			{
				throw new TypeConflictException("right of StringNumberBitRTwoSideSameNumberBitOrOneString is not number bit or string.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (pre.getCls() == String.class || post.getCls() == String.class)
		{
			return new CCType(String.class, "String");
		}
		return post;
	}
	
}
