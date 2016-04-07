package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
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
			cnode.getData().setStructsignal(null);
			// TODO
		}
		else
		{
			throw new CodeSynthesisException("Wrong access block, there is no corresponding.");
		}
		return null;
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