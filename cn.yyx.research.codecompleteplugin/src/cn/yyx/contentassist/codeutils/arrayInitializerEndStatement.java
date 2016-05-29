package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArrayInitializerEndData;
import cn.yyx.contentassist.codesynthesis.data.CSArrayInitializerStartData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class arrayInitializerEndStatement extends statement{

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.SymbolMark);
		cs.AddOneData("}", null);
		return false;
	}*/

	public arrayInitializerEndStatement(String smtcode) {
		super(smtcode);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayInitializerEndStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayInitializerEndStatement)
		{
			return 1;
		}
		return 0;
	}

	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer res = cstack.peek();
		if (res == null || res != StructureSignalMetaInfo.ArrayInitialBlock)
		{
			return true;
		}
		cstack.pop();
		return false;
	}*/
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.ArrayInitialBlock);
		FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSArrayInitializerStartData.class, signals);
		if (cnode != null)
		{
			List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
			FlowLineNode<CSFlowLineData> nl = new FlowLineNode<CSFlowLineData>(new CSArrayInitializerEndData(squeue.GenerateNewNodeId(), smthandler.getSete(), "}", null, null, squeue.GetLastHandler()), smthandler.getProb());
			result.add(nl);
			CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, nl, cnode);
			return result;
		}
		else
		{
			throw new CodeSynthesisException("Wrong access block, there is no corresponding.");
		}
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> cnode = cstack.GetSearchedAndHandledBlockStart();
		if (cnode == null)
		{
			throw new CodeSynthesisException("Wrong access block, there is no corresponding.");
		}
		return false;
	}
	
}