package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class classRef extends type {
	
	int scope = -1;
	int off = -1;
	
	public classRef(int scope2, int off2) {
		this.scope = scope2;
		this.off = off2;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((classRef) t).scope, off, ((classRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((classRef) t).scope, off, ((classRef) t).off);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		
		return false;
	}
	
}