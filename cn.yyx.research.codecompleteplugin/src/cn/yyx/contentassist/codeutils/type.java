package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public abstract class type implements OneCode{
	
	/**
	 * needs to generate the variable name.
	 * @param squeue
	 * @param smthandler
	 * @return
	 * @throws CodeSynthesisException
	 */
	public abstract List<FlowLineNode<CSFlowLineData>> HandleArgumentType(CSFlowLineQueue squeue, CSStatementHandler smthandler, char seed)
			throws CodeSynthesisException;
	
}
