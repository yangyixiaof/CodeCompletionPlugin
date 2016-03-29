package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisHelper;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class intersectionType extends type{
	
	List<type> tps = null;
	
	public intersectionType(List<type> tps) {
		this.tps = tps;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof intersectionType)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((intersectionType) t).tps);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof intersectionType)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((intersectionType) t).tps);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CodeSynthesisHelper.HandleIntersectionOrUnionType(squeue, expected, handler, result, ai, tps, "&");
		return false;
	}
	
}