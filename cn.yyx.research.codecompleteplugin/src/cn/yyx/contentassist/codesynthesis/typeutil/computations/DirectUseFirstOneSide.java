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
		brtssnb.setPost((CCType) post.clone());
		brtssnb.setPre((CCType) pre.clone());
		return brtssnb;
	}
	
}