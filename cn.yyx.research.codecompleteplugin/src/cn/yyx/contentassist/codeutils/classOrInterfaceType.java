package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class classOrInterfaceType extends type{
	
	List<type> tps = null;
	
	public classOrInterfaceType(List<type> result) {
		this.tps = result;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((classOrInterfaceType) t).tps);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((classOrInterfaceType) t).tps);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		
		return false;
	}
	
}