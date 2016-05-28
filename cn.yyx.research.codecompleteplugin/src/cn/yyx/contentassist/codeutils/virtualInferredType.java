package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.NameConvention;

public class virtualInferredType extends type{
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof virtualInferredType)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof virtualInferredType)
		{
			return 1;
		}
		return 0;
	}

	@Override
	@Deprecated
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// CheckUtil.CheckStatementHandlerIsArgTypeStatementHandler(smthandler);
		// CSArgTypeStatementHandler ats = (CSArgTypeStatementHandler)smthandler;
		return null;
	}
	
	public List<FlowLineNode<CSFlowLineData>> HandleVirtualInferredTypeCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler, char c)
			throws CodeSynthesisException {
		String returntype = "Infer" + c;
		String modifidedname = squeue.GetLastHandler().getScopeOffsetRefHandler().GenerateNewDeclaredVariable(NameConvention.GetAbbreviationOfType(returntype), returntype, null, smthandler.getAoi().isInFieldLevel());
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), modifidedname, null, false, false, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
}