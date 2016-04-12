package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class finalVarRef extends identifier{
	
	int scope = -1;
	int off = -1;
	
	public finalVarRef(int scope, int off) {
		this.scope = scope;
		this.off = off;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof finalVarRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((finalVarRef) t).scope, off, ((finalVarRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof finalVarRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((finalVarRef) t).scope, off, ((finalVarRef) t).off);
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CheckUtil.CanNeverReachHere("finalVarRef is not just handled.");
		return null;
	}
	
}