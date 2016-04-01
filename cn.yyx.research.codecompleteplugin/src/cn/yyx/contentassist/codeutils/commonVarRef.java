package cn.yyx.contentassist.codeutils;

import java.util.Map;
import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		// ErrorUtil.CheckDirectlyMemberHintInAINotNull(ai);
		Map<String, String> po = handler.getScopeOffsetRefHandler().HandleCommonVariableRef(scope, off);
		CodeSynthesisHelper.HandleVarRefCodeSynthesis(po, squeue, expected, handler, result, ai);
		return false;
	}
	
}