package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class commonVarRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public commonVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonVarRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((commonVarRef) t).scope, off, ((commonVarRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonVarRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((commonVarRef) t).scope, off, ((commonVarRef) t).off);
		}
		return 0;
	}
	
}
