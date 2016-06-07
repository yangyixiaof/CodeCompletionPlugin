package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSVarRefHelper;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetResult;

public class commonVarRef extends referedExpression{
	
	int scope = -1;
	int off = -1;
	
	public commonVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonVarRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((commonVarRef) t).scope, off, ((commonVarRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonVarRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((commonVarRef) t).scope, off, ((commonVarRef) t).off);
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// Map<String, String> 
		ScopeOffsetResult po = CSVarRefHelper.GetAllCommonTypeVariablePair(squeue, smthandler, scope, off);
		return CodeSynthesisHelper.HandleVarRefCodeSynthesis(po, squeue, smthandler);
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		ScopeOffsetResult po = CSVarRefHelper.GetAllCommonTypeVariablePair(squeue, smthandler, scope, off);
		return CodeSynthesisHelper.HandleVarRefInferredField(po, squeue, smthandler, reservedword, expectedinfer);
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		ScopeOffsetResult po = CSVarRefHelper.GetAllCommonTypeVariablePair(squeue, smthandler, scope, off);
		return CodeSynthesisHelper.HandleVarRefInferredMethodReference(po, squeue, smthandler, reservedword, expectedinfer);
	}
	
}