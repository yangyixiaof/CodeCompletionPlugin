package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Map;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class commonFieldRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public commonFieldRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonFieldRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((commonFieldRef) t).scope, off, ((commonFieldRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonFieldRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((commonFieldRef) t).scope, off, ((commonFieldRef) t).off);
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		Map<String, String> po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(scope, off);
		return CodeSynthesisHelper.HandleVarRefCodeSynthesis(po, squeue, smthandler);
	}
	
}