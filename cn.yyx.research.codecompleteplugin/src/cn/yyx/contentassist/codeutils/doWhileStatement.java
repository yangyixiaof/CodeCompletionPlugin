package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSInnerLevelPreHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.CSFlowLineTypeCheckRefiner;

public class doWhileStatement extends statement{
	
	referedExpression rexp = null;
	
	public doWhileStatement(String smtcode, referedExpression rexp) {
		super(smtcode);
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof doWhileStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((doWhileStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		// CSFlowLineHelper.ConcateOneFlowLineList("do {\n\n} while (", rels, ");");
		CSInnerLevelPreHandler csilp = new CSInnerLevelPreHandler("do-while", smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csilp);
		if (rels == null || rels.size() == 0)
		{
			return null;
		}
		return CSFlowLineTypeCheckRefiner.RetainTheFallThroughFlowLineNodes(CSFlowLineHelper.ConcateOneFlowLineList("do " + CodeSynthesisHelper.GenerateBlockCode(smthandler) + " while (", rels, ");"), new CCType(Boolean.class, "Boolean"));
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}