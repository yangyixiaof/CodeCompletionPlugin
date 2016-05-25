package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForExpOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSForIniOverProperty;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class rawForExpOverStatement extends statement {
	
	public rawForExpOverStatement(String smtcode) {
		super(smtcode);
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId() + "", smthandler.getSete(), ";", null, true, true, null, null, squeue.GetLastHandler(), CSForExpOverProperty.GetInstance()), smthandler.getProb()));
		return result;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof rawForExpOverStatement || t instanceof forExpOverStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof rawForExpOverStatement)
		{
			return 1;
		}
		if (t instanceof forExpOverStatement)
		{
			return 0.5;
		}
		return 0;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.CommonForExpWaitingOver);
		FlowLineNode<CSFlowLineData> cnode = cstack.BackSearchForFirstSpecialClass(CSForIniOverProperty.class, signals);
		if (cnode == null)
		{
			throw new CodeSynthesisException("for exp over does not have init over in pre.");
		}
		return false;
	}
	
}