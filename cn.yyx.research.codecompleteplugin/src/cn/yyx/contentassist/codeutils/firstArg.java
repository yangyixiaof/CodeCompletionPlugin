package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Map;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;

public abstract class firstArg implements OneCode {
	
	public abstract List<FlowLineNode<CSFlowLineData>> HandleClassOrMethodInvoke(CSFlowLineQueue squeue, CSStatementHandler smthandler, String methodname, Map<String, MethodTypeSignature> mts)
			throws CodeSynthesisException;
	
}