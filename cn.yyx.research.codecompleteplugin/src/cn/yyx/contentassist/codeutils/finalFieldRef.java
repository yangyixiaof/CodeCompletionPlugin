package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.ErrorUtil;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class finalFieldRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public finalFieldRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof finalFieldRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((finalFieldRef) t).scope, off, ((finalFieldRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof finalFieldRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((finalFieldRef) t).scope, off, ((finalFieldRef) t).off);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		ErrorUtil.CanNeverReachHere("finalVarRef is not just handled.");
		return false;
	}
	
}