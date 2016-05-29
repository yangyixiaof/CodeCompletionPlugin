package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class InheritLeftRightNumbetBit extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (!TypeComputer.IsStrictNumberBit(post.getCls()))
			{
				throw new TypeConflictException("right of inherit not number bit.");
			}
		}
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return pre;
	}

}
