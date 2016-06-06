package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class CondQuestion extends TypeComputationKind{
	
	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (pre.getCls() != boolean.class && pre.getCls() != Boolean.class && !(pre instanceof InferredCCType))
			{
				throw new TypeConflictException("left of CondQuestion not boolean. is:" + pre);
			}
		}
	}
	
	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
	}
	
	@Override
	public CCType HandleResult() throws TypeConflictException {
		return post;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		CondQuestion tcmp = new CondQuestion();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}
	
}