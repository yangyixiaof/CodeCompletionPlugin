package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;

public class voidType extends type {

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "void", new CCType(void.class, "void"), null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof voidType)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof voidType)
		{
			return 1;
		}
		return 0;
	}
	
}