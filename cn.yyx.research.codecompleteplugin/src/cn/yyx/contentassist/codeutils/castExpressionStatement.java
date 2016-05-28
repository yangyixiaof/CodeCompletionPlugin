package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.LeftOrRightCast;

public class castExpressionStatement extends expressionStatement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public castExpressionStatement(String smtcode, type tp, referedExpression rexp) {
		super(smtcode);
		this.tp = tp;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return 0.4 + 0.6*(tp.Similarity(((castExpressionStatement) t).tp) + rexp.Similarity(((castExpressionStatement) t).rexp));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ttp = new CSNode(CSNodeType.HalfFullExpression);
		tp.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
		ttp.setPrefix("(");
		ttp.setPostfix(")");
		
		expected.push(null);
		CSNode resb = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, resb, null);
		if (conflict)
		{
			return true;
		}
		CSNode fin = new CSNode(CSNodeType.WholeStatement);
		fin.setDatas(CSNodeHelper.ConcatTwoNodesDatas(ttp, resb, null, -1));
		squeue.add(fin);
		expected.pop();
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		
		// debugging code, not remove.
		if (rels == null || tpls == null)
		{
			System.err.println("rels == null");
		}
		
		return CSFlowLineHelper.ForwardConcate("(", tpls, ")", rels, null, squeue, smthandler, new LeftOrRightCast());
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}