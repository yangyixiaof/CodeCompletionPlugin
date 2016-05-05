package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.Utility.StringUtil;

public class arrayType extends type {
	
	type tp = null;
	int count = 0;
	
	public arrayType(type tp, int count) {
		this.tp = tp;
		this.count = count;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayType)
		{
			if (count == ((arrayType)t).count)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayType)
		{
			return 0.4 + 0.6*(0.7*SimilarityHelper.ComputeTwoIntegerSimilarity(count, ((arrayType) t).count) + 0.3*(tp.Similarity(((arrayType) t).tp)));
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ttp = new CSNode(CSNodeType.HalfFullExpression);
		tp.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
		
		ttp.setPostfix(dimens);
		squeue.add(ttp);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> ls = tp.HandleCodeSynthesis(squeue, smthandler);
		String dimens = StringUtil.GenerateDuplicates("[]", count);
		return CSFlowLineHelper.ConcateOneFlowLineList(null, ls, dimens);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleArgumentType(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			char seed) throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}