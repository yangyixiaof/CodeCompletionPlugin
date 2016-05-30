package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

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
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = null;
		type tp = tps.iterator().next();
		result = tp.HandleCodeSynthesis(squeue, smthandler);
		/*Iterator<type> itr = reveredtps.iterator();
		while (itr.hasNext())
		{
			type tp = itr.next();
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			if (tpls == null || tpls.size() == 0)
			{
				continue;
			}
			if (result == null)
			{
				result = tpls;
			}
			else
			{
				if (result.size() == 0)
				{
					break;
				}
				result = CodeSynthesisHelper.HandleTypeSpecificationInfer(result, tpls, squeue, smthandler);
			}
		}*/
		if (result == null || result.size() == 0)
		{
			return CodeSynthesisHelper.HandleMultipleConcateType(squeue, smthandler, tps, ".");
		}
		return result;
	}
	
}