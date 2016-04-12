package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public class doWhileStatement extends statement{
	
	referedExpression rexp = null;
	
	public doWhileStatement(referedExpression rexp) {
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

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ts = new CSNode(CSNodeType.TempUsed);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, ts, null);
		if (conflict)
		{
			return true;
		}
		ts.setContenttype(CSNodeType.WholeStatement);
		ts.setPrefix("do {\n\n} while (");
		ts.setPostfix(");");
		squeue.add(ts);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		CSFlowLineHelper.ConcateOneFLStamp("do {\n\n} while (", rels, ");");
		return null;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}