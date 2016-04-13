package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeResolver;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

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
		String tp = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleTypeRef(off);
		Class<?> c = TypeResolver.ResolveType(tp, squeue.GetLastHandler().getContextHandler().getJavacontext());
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), tp, c, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
}