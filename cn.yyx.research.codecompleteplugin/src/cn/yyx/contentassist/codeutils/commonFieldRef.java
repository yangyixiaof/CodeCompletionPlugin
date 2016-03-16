package cn.yyx.contentassist.codeutils;

import java.util.Map;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.ErrorUtil;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.TypeCheckHelper;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class commonFieldRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public commonFieldRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonFieldRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((commonFieldRef) t).scope, off, ((commonFieldRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonFieldRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((commonFieldRef) t).scope, off, ((commonFieldRef) t).off);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		ErrorUtil.CheckDirectlyMemberHintInAINotNull(ai);
		Map<String, String> po = handler.HandleFieldVariableRef(scope, off);
		String hint = ai.getDirectlyMemberHint();
		result.append(TypeCheckHelper.GetMostLikelyRef(po, hint));
		return false;
	}
	
}