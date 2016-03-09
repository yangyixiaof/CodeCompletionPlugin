package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class finalVarRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public finalVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof finalVarRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((finalVarRef) t).scope, off, ((finalVarRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof finalVarRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((finalVarRef) t).scope, off, ((finalVarRef) t).off);
		}
		return 0;
	}
	
}
