package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class catchClauseStatement extends statement{
	
	type tp = null;
	
	public catchClauseStatement(type rt) {
		this.tp = rt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof catchClauseStatement)
		{
			return 0.5 + 0.5*(tp.Similarity(((catchClauseStatement)t).tp));
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ttp = new CSNode(CSNodeType.HalfFullExpression);
		tp.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
		ttp.setPrefix("catch (");
		ttp.setPostfix(" e)");
		squeue.add(ttp);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineList("catch (", tpls, " e)");
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}