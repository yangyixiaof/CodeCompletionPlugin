package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;

public class prefixUnaryOperator implements OneCode{
	
	String optr = null;
	
	public prefixUnaryOperator(String text) {
		this.optr = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof prefixUnaryOperator)
		{
			if (optr.equals(((prefixUnaryOperator) t).optr))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof prefixUnaryOperator)
		{
			double prob = 0;
			if (optr.equals(((prefixUnaryOperator) t).optr))
			{
				prob = 1;
			}
			return 0.5 + 0.5 * prob;
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		result.setContenttype(CSNodeType.SymbolMark);
		result.AddOneData(optr, null);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// TODO this operation may cause error.
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		TypeComputationKind tc = TypeComputer.ComputeKindFromRawString(optr);
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), optr, null, false, true, tc, tc, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
}