package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class fieldAccessStatement extends expressionStatement{
	
	fieldAccess fa = null;
	
	public fieldAccessStatement(String smtcode, fieldAccess fa) {
		super(smtcode);
		this.fa = fa;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldAccessStatement)
		{
			if (fa.CouldThoughtSame(((fieldAccessStatement) t).fa))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldAccessStatement)
		{
			return 0.3 + 0.7*(fa.Similarity(((fieldAccessStatement) t).fa));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		fa.HandleCodeSynthesis(squeue, expected, handler, fcs, null);
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return fa.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}