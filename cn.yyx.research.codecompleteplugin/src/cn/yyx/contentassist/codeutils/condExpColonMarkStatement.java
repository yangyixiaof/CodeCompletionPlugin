package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSCondExpColonMarkProperty;
import cn.yyx.contentassist.codesynthesis.data.CSCondExpQuestionMarkProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.CondColon;

public class condExpColonMarkStatement extends statement{

	public condExpColonMarkStatement(String smtcode) {
		super(smtcode);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof condExpColonMarkStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof condExpColonMarkStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.ConditionExpressionColon);
		FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForTheNextOfSpecialClass(CSCondExpQuestionMarkProperty.class, signals);
		if (cnode == null)
		{
			throw new CodeSynthesisException("Condition Expression does not have begin?");
		}
		CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, smthandler, squeue.getLast(), cnode);
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId()+"", smthandler.getSete(), " : ", null, new CondColon(), squeue.GetLastHandler(), new CSCondExpColonMarkProperty(null)), smthandler.getProb()));
		return result;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.ConditionExpressionColon);
		FlowLineNode<CSFlowLineData> cnode = cstack.BackSearchForFirstSpecialClass(CSCondExpQuestionMarkProperty.class, signals);
		if (cnode == null)
		{
			throw new CodeSynthesisException("No conditional expression start signal.");
		}
		return false;
	}
	
}