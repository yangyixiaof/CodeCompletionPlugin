package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class NumberBitROneOrTwoSideSameNumberBit extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		// TODO Auto-generated method stub

	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean HandleOver() throws TypeConflictException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CCType HandleResult() throws TypeConflictException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean PreIsHandled(CCType pre) throws TypeConflictException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean PostIsHandled(CCType pre) throws TypeConflictException {
		// TODO Auto-generated method stub
		return false;
	}

}
