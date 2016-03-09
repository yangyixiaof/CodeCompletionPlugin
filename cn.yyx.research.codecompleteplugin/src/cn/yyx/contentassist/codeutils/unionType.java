package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

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
	
}