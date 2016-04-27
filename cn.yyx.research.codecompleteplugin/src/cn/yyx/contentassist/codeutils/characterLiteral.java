package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

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
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), literal, null, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public void HandleNegativeOperator() {
		// TODO Auto-generated method stub
		
	}
	
}