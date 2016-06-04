package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class DirectUseSecondOneSide extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = post;
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		this.pre = post;
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (pre instanceof InferredCCType || post instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		return post;
	}
	
}
