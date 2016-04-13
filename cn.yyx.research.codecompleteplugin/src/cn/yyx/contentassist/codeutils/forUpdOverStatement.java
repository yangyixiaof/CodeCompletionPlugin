package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.StructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class forUpdOverStatement extends statement {

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forUpdOverStatement) {
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forUpdOverStatement) {
			return 1;
		}
		return 0;
	}

	/*
	 * @Override public boolean HandleOverSignal(Stack<Integer> cstack) { int
	 * signal = cstack.peek(); if (signal !=
	 * StructureSignalMetaInfo.CommonForExpWaitingOver) { return true; //
	 * System.err.println("What the fuck, pre is not for?"); // new
	 * Exception().printStackTrace(); // System.exit(1); } else { cstack.pop();
	 * } return false; }
	 * 
	 * @Override public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue,
	 * Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result,
	 * AdditionalInfo ai) { CSNode cs = new
	 * CSNode(CSNodeType.HalfFullExpression); cs.AddOneData("", null);
	 * squeue.add(cs); return false; }
	 */

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(),
				") {\n}", null, null, true, TypeComputationKind.NoOptr, squeue.GetLastHandler()),
				smthandler.getProb()));
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> cnode = cstack
				.BackSearchForStructureSignal(StructureSignalMetaInfo.CommonForExpWaitingOver);
		if (cnode != null) {
			cnode.getData().setStructsignal(null);
		} else {
			throw new CodeSynthesisException("for exp over does not have init over in pre.");
		}
		return false;
	}

}