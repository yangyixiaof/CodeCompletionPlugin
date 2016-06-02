package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class StringNumberBitRTwoSideSameNumberBitOrOneString extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (!TypeComputer.IsNumberBit(pre.getCls()) && pre.getCls() != String.class && !(pre instanceof InferredCCType))
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
			if (!TypeComputer.IsNumberBit(post.getCls()) && post.getCls() != String.class && !(post instanceof InferredCCType))
			{
				throw new TypeConflictException("right of StringNumberBitRTwoSideSameNumberBitOrOneString is not number bit or string.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (pre instanceof InferredCCType || post instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (pre.getCls() == String.class || post.getCls() == String.class)
		{
			return new CCType(String.class, "String");
		}
		return post;
	}
	
}
