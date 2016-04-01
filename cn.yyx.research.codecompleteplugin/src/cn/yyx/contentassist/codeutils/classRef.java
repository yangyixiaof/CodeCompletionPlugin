package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		String tp = handler.getScopeOffsetRefHandler().HandleTypeRef(off);
		result.AddOneData(tp, null);
		return false;
	}
	
}