package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeResolver;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.JDTManager.GCodeMetaInfo;

public class classRef extends type {
	
	int scope = -1;
	int off = -1;
	
	public classRef(int scope2, int off2) {
		this.scope = scope2;
		this.off = off2;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classRef)
		{
			return SimilarityHelper.CouldThoughtScopeOffsetSame(scope, ((classRef) t).scope, off, ((classRef) t).off);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classRef)
		{
			return SimilarityHelper.ComputeScopeOffsetSimilarity(scope, ((classRef) t).scope, off, ((classRef) t).off);
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		String tp = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleTypeRef(GCodeMetaInfo.HackedNoType, 0, off);
		if (tp == null)
		{
			return null;
		}
		LinkedList<CCType> clss = TypeResolver.ResolveType(tp, squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<CCType> itr = clss.iterator();
		while (itr.hasNext())
		{
			CCType cct = itr.next();
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), cct.getClstr(), cct, null, squeue.GetLastHandler()), smthandler.getProb()));
		}
		return result;
	}
	
}