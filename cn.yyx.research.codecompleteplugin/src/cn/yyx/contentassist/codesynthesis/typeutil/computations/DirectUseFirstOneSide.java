package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class DirectUseFirstOneSide extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = pre;
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return pre;
	}
	
}
