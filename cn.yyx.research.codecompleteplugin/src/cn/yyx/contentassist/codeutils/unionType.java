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

public class unionType extends type{
	
	List<type> tps = null;
	
	public unionType(List<type> tps) {
		this.tps = tps;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof unionType)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((unionType) t).tps);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof unionType)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((unionType) t).tps);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CodeSynthesisHelper.HandleIntersectionOrUnionType(squeue, expected, handler, result, ai, tps, "|");
		return false;
	}
	
}