package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class variableDeclarationStatement extends statement{
	
	type tp = null;
	
	public variableDeclarationStatement(type tp) {
		this.tp = tp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			if (tp.CouldThoughtSame(((variableDeclarationStatement) t).tp))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			return 0.5 + 0.5*(tp.Similarity(((variableDeclarationStatement) t).tp));
		}
		return 0;
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode tpcs = new CSNode(CSNodeType.VariableDeclaration);
		boolean conflict = tp.HandleCodeSynthesis(squeue, expected, handler, tpcs, ai);
		if (conflict)
		{
			return true;
		}
		handler.setRecenttype(tpcs.GetFirstDataWithoutTypeCheck());
		squeue.add(tpcs);
		return false;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}