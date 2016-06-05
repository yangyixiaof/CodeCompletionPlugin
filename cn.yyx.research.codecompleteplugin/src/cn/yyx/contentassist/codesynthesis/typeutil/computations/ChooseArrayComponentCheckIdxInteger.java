package cn.yyx.contentassist.codesynthesis.typeutil.computations;

import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class ChooseArrayComponentCheckIdxInteger extends TypeComputationKind {

	@Override
	public void HandlePre(CCType pre) throws TypeConflictException {
		this.pre = pre;
		if (pre != null)
		{
			if (pre.getCls() == null || !pre.getCls().isArray())
			{
				throw new TypeConflictException("type array error.");
			}
		}
	}

	@Override
	public void HandlePost(CCType post) throws TypeConflictException {
		this.post = post;
		if (post != null)
		{
			if (post.getCls() == null || !(post.getCls() == int.class || post.getCls() == Integer.class))
			{
				throw new TypeConflictException("type idx not int.");
			}
		}
	}

	@Override
	public CCType HandleResult() throws TypeConflictException {
		String arrays = pre.getClstr();
		int idx = arrays.lastIndexOf("[]");
		if (idx < 0)
		{
			System.err.println("What the fuck. array str has no []???");
			new Exception("What the fuck. array str has no []???").printStackTrace();
			System.exit(1);
		}
		String pstr = arrays.substring(0, idx);
		return new CCType(pre.getCls().getComponentType(), pstr);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ChooseArrayComponentCheckIdxInteger tcmp = new ChooseArrayComponentCheckIdxInteger();
		CCType postc = post == null ? null : (CCType) post.clone();
		CCType prec = pre == null ? null : (CCType) pre.clone();
		tcmp.setPost(postc);
		tcmp.setPre(prec);
		return tcmp;
	}
	
}