package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class DirectUseFirstOneSide extends TypeComputationKind {
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.setPre(pre);
		this.setPost(pre);
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.setPost(getPre());
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		if (getPre() instanceof InferredCCType || getPost() instanceof InferredCCType)
		{
			return new InferredCCType();
		}
		return getPre();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		DirectUseFirstOneSide brtssnb = new DirectUseFirstOneSide();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		brtssnb.setPost(postc);
		brtssnb.setPre(prec);
		return brtssnb;
	}
	
}