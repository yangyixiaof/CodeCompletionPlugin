package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

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
	
}