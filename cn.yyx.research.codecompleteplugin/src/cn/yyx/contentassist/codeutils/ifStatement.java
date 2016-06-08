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
import cn.yyx.contentassist.codesynthesis.typeutil.WhiteNull;
import cn.yyx.contentassist.commonutils.ListHelper;

public class ifStatement extends statement{
	
	referedExpression rexp = null;
	
	public ifStatement(String smtcode, referedExpression rexp) {
		super(smtcode);
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof ifStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof ifStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((ifStatement) t).rexp));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CSInnerLevelPreHandler csilp = new CSInnerLevelPreHandler("if", smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csilp);
		if (rels == null || rels.size() == 0)
		{
			return null;
		}
		List<FlowLineNode<CSFlowLineData>> ifls = CSFlowLineTypeCheckRefiner.RetainTheFallThroughFlowLineNodes(CSFlowLineHelper.ConcateOneFlowLineList("if (", rels, ") " + CodeSynthesisHelper.GenerateBlockCode(smthandler)), new CCType(Boolean.class, "Boolean"));
		ListHelper.SetDclsToAllListNodes(ifls, new CCType(WhiteNull.class, "WhiteNull"));
		return ifls;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}