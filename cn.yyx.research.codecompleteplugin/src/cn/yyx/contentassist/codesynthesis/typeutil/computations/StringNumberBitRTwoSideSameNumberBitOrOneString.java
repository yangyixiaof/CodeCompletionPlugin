package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class StringNumberBitRTwoSideSameNumberBitOrOneString extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
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
		this.setPost(post);
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
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		if (getPre().getCls() == String.class || getPost().getCls() == String.class)
		{
			return new CCType(String.class, "String");
		}
		return getPost();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		StringNumberBitRTwoSideSameNumberBitOrOneString brtssnb = new StringNumberBitRTwoSideSameNumberBitOrOneString();
		brtssnb.setPost((CCType) post.clone());
		brtssnb.setPre((CCType) pre.clone());
		return brtssnb;
	}
	
}