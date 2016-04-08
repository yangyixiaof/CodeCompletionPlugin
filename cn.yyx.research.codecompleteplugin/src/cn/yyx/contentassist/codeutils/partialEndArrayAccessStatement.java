package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

public class partialEndArrayAccessStatement extends statement{
	
	expressionStatement es = null;
	
	public partialEndArrayAccessStatement(expressionStatement es) {
		this.es = es;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return es.CouldThoughtSame(((partialEndArrayAccessStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((partialEndArrayAccessStatement) t).es));
		}
		return 0;
	}

	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer res = cstack.peek();
		if (res == null || res != StructureSignalMetaInfo.ArrayAccessBlcok)
		{
			return true;
		}
		cstack.pop();
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		boolean conflict = es.HandleCodeSynthesis(squeue, expected, handler, result, ai);
		if (conflict)
		{
			return true;
		}
		squeue.getLast().setPostfix("]");
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		squeue.SetLastHasHole();
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "]", null, null, false, TypeComputationKind.NoOptr, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> cnode = cstack.BackSearchForStructureSignal(StructureSignalMetaInfo.ArrayAccessBlcok);
		if (cnode != null)
		{
			cnode.getData().setStructsignal(null);
		}
		else
		{
			throw new CodeSynthesisException("ArrayAccessBlcok disappeared.");
		}
		return false;
	}
	
}