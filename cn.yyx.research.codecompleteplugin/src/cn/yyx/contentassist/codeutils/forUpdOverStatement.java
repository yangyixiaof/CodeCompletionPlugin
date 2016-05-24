package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForExpOverData;
import cn.yyx.contentassist.codesynthesis.data.CSForUpdOverData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class forUpdOverStatement extends statement {
	
	statement smt = null;
	
	public forUpdOverStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO 22222222222222
		if (t instanceof forUpdOverStatement) {
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		// TODO 22222222222222
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
		// TODO 22222222222222
		FlowLineNode<CSFlowLineData> fln = new FlowLineNode<CSFlowLineData>(new CSForUpdOverData(squeue.GenerateNewNodeId(), smthandler.getSete(),
				") {\n}", null, true, true, null, null, squeue.GetLastHandler()),
				smthandler.getProb());
		return CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, null);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.CommonForUpdWaitingOver);
		FlowLineNode<CSFlowLineData> cnode = cstack.BackSearchForFirstSpecialClass(CSForExpOverData.class, signals);
		if (cnode == null)
		{
			throw new CodeSynthesisException("for upd over does not have init over in pre.");
		}
		return true;
	}

}