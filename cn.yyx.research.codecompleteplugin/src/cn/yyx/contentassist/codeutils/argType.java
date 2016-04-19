package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class argType implements OneCode{
	
	type tp = null;
	
	public argType(type tp) {
		this.tp = tp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argType)
		{
			if (tp.CouldThoughtSame(((argType) t).tp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argType)
		{
			return 0.3+0.7*(tp.Similarity(((argType) t).tp));
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		if (!(tp instanceof virtualInferredType))
		{
			CodeSynthesisHelper.DirectlyGenerateNameOfType(tpls, squeue, smthandler);
		}
		return tpls;
	}
	
}