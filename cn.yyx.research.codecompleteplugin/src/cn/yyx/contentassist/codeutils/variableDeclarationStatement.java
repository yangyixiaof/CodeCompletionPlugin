package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableDeclarationData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class variableDeclarationStatement extends statement{
	
	type tp = null;
	
	public variableDeclarationStatement(String smtcode, type tp) {
		super(smtcode);
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
			return 0.4 + 0.6*(tp.Similarity(((variableDeclarationStatement) t).tp));
		}
		return 0;
	}
		
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		if (tpls == null || tpls.size() == 0)
		{
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), smtcode.substring("VD@".length()), null, false, false, null, squeue.GetLastHandler()), smthandler.getProb()));
			return result;
		}
		Iterator<FlowLineNode<CSFlowLineData>> itr = tpls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			result.add(new FlowLineNode<CSFlowLineData>(new CSVariableDeclarationData(fln.getData().getData().trim(), fln.getData()), fln.getProbability()));
		}
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}