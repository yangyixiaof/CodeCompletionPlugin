package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.ErrorUtil;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		ErrorUtil.CanNeverReachHere("finalVarRef is not just handled.");
		return false;
	}
	
}