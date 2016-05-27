package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class chainFieldAccess extends fieldAccess {
	
	identifier id = null;
	fieldAccess fa = null;
	
	public chainFieldAccess(identifier id, fieldAccess fa) {
		this.id = id;
		this.fa = fa;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> fals = fa.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ForwardConcate(null, fals, ".", idls, null, squeue, smthandler, null, null);
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof chainFieldAccess)
		{
			if (id.CouldThoughtSame(((chainFieldAccess) t).id) || fa.CouldThoughtSame(((chainFieldAccess) t).fa))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof chainFieldAccess)
		{
			if (id.CouldThoughtSame(((chainFieldAccess) t).id) || fa.CouldThoughtSame(((chainFieldAccess) t).fa))
			{
				return 0.2 + 0.4*(id.Similarity(((chainFieldAccess) t).id)) + 0.4*(fa.Similarity(((chainFieldAccess) t).fa));
			}
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredField(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredMethodReference(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}
	
}