package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		Iterator<type> itr = tps.iterator();
		while (itr.hasNext())
		{
			type t = itr.next();
			CSNode ttp = new CSNode(CSNodeType.TempUsed);
			boolean conflict = t.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
			if (conflict)
			{
				return true;
			}
			result.setDatas(CSNodeHelper.ConcatTwoNodesDatas(ttp, result, ".", -1));
		}
		CSNodeHelper.HandleTypeByTypeCodes(result);
		return false;
	}
	
}