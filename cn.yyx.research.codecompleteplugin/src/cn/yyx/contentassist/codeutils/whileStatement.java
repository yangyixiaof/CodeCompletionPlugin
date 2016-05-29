package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSInnerLevelPreHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.CSFlowLineTypeCheckRefiner;

public class whileStatement extends statement{
	
	referedExpression rexp = null;
	
	public whileStatement(String smtcode, referedExpression rexp) {
		super(smtcode);
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof whileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof whileStatement)
		{
			return 0.5 + 0.5*(rexp.Similarity(((whileStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CSInnerLevelPreHandler csilp = new CSInnerLevelPreHandler("while", smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csilp);
		if (rels == null || rels.size() == 0)
		{
			return null;
		}
		return CSFlowLineTypeCheckRefiner.RetainTheFallThroughFlowLineNodes(CSFlowLineHelper.ConcateOneFlowLineList("while (", rels, ") {\n}"), new CCType(Boolean.class, "Boolean"));
		// return CSFlowLineHelper.ConcateOneFlowLineList("while (", rels, ") {\n\n}");
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}

}