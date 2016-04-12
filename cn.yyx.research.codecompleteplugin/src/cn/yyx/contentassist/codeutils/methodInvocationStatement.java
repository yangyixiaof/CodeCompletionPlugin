package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public class methodInvocationStatement extends expressionStatement{
	
	identifier id = null;
	argumentList arglist = null;
	
	public methodInvocationStatement(identifier name, argumentList argList) {
		this.id = name;
		this.arglist = argList;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodInvocationStatement)
		{
			if (id.CouldThoughtSame(((methodInvocationStatement) t).id) || arglist.CouldThoughtSame(((methodInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodInvocationStatement)
		{
			return 0.3 + 0.7*(0.5*(id.Similarity(((methodInvocationStatement) t).id)) + 0.5*(arglist.Similarity(((methodInvocationStatement) t).arglist)));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode nacs = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = name.HandleCodeSynthesis(squeue, expected, handler, nacs, ai);
		if (conflict)
		{
			return true;
		}
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(nacs.GetFirstDataWithoutTypeCheck());
		nai.setDirectlyMemberIsMethod(true);
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		argList.HandleCodeSynthesis(squeue, expected, handler, fcs, nai);
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> nls = id.HandleCodeSynthesis(squeue, smthandler);
		String methodname = nls.get(0).getData().getData();
		CSMethodStatementHandler csmsh = new CSMethodStatementHandler(methodname, smthandler);
		csmsh.setNextstart(squeue.getLast());
		return arglist.HandleCodeSynthesis(squeue, csmsh);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}