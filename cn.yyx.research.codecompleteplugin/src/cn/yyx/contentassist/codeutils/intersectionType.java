package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeHelper;
import cn.yyx.contentassist.commonutils.CSNodeType;
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
		Iterator<type> itr = tps.iterator();
		type tp = itr.next();
		CSNode tp1 = new CSNode(CSNodeType.TempUsed);
		tp.HandleCodeSynthesis(squeue, expected, handler, tp1, ai);
		while (itr.hasNext())
		{
			type ttp = itr.next();
			CSNode tp2 = new CSNode(CSNodeType.TempUsed);
			ttp.HandleCodeSynthesis(squeue, expected, handler, tp2, ai);
			CSNode mgd = new CSNode(CSNodeType.TempUsed);
			mgd.setDatas(CSNodeHelper.ConcatTwoNodesDatas(tp1, tp2, "&", -1));
			tp1 = mgd;
		}
		result.setDatas(tp1.getDatas());
		return false;
	}
	
}