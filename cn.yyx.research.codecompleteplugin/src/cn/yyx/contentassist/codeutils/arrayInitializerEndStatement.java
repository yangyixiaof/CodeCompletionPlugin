package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

public class arrayInitializerEndStatement extends statement{

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.SymbolMark);
		cs.AddOneData("}", null);
		return false;
	}*/

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
		FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForStructureSignal(StructureSignalMetaInfo.ArrayInitialBlock);
		if (cnode != null)
		{
			squeue.SetLastHasHole();
			cnode.getData().setStructsignal(null);
			List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
			FlowLineNode<CSFlowLineData> nl = new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "}", null, null, false, TypeComputationKind.NoOptr, squeue.GetLastHandler()), smthandler.getProb());
			result.add(nl);
			return CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, nl, cnode);
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