package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class typeList extends OneCode {
	
	private List<type> tps = new LinkedList<type>();
	
	public typeList() {
	}
	
	public void AddToFirst(type re)
	{
		tps.add(0, re);
	}
	
	public void AddReferedExpression(type re)
	{
		tps.add(re);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeList)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((typeList) t).tps);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeList)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((typeList) t).tps);
		}
		return 0;
	}
}
