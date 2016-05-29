package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;

public class characterLiteral extends literal{
	
	String literal = null;
	
	public characterLiteral(String text) {
		if (text.equals("'@w'"))
		{
			text = "' '";
		}
		this.literal = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof characterLiteral)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof characterLiteral)
		{
			return 0.6 + 0.4*(literal.equals(((characterLiteral) t).literal) ? 1 : 0);
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		result.setContenttype(CSNodeType.SymbolMark);
		result.AddOneData(literal, null);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), literal, new CCType(char.class, "char"), null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public void HandleNegativeOperator() throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("char literal should handle negative operator?");
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("characterLiteral should handle InferredField?");
		return null;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("characterLiteral should handle InferredMethodReference?");
		return null;
	}
	
}