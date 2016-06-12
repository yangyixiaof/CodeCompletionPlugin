package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class DirectlySetNull extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(new CCType(null, null));
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.setPost(new CCType(null, null));
	}

	@Override
	public CCType HandleResult() throws TypeConflictException {
		return null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		DirectlySetNull tcmp = new DirectlySetNull();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}
	
}