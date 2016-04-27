package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodSignalHandleResult;
import cn.yyx.contentassist.codesynthesis.data.CSPrData;
import cn.yyx.contentassist.codesynthesis.data.CSPsData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class commonMethodInvocationStatement extends expressionStatement{
	
	identifier id = null;
	argumentList arglist = null;
	
	public commonMethodInvocationStatement(identifier name, argumentList argList) {
		this.id = name;
		this.arglist = argList;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonMethodInvocationStatement)
		{
			if (id.CouldThoughtSame(((commonMethodInvocationStatement) t).id) || arglist.CouldThoughtSame(((commonMethodInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonMethodInvocationStatement)
		{
			return 0.3 + 0.7*(0.5*(id.Similarity(((commonMethodInvocationStatement) t).id)) + 0.5*(arglist.Similarity(((commonMethodInvocationStatement) t).arglist)));
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
		CSFlowLineData tlast = squeue.getLast().getData();
		boolean hasem = false;
		if ((tlast instanceof CSPsData) || (tlast instanceof CSPrData))
		{
			hasem = true;
		}
		CSMethodSignalHandleResult csmshr = squeue.BackSearchForMethodRelatedSignal();
		List<FlowLineNode<CSFlowLineData>> nls = id.HandleCodeSynthesis(squeue, smthandler);
		String methodname = nls.get(0).getData().getData();
		CSMethodStatementHandler csmsh = new CSMethodStatementHandler(methodname, smthandler);
		csmsh.setNextstart(squeue.getLast());
		List<FlowLineNode<CSFlowLineData>> alls = arglist.HandleCodeSynthesis(squeue, csmsh);
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = alls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSMethodInvocationData dt = new CSMethodInvocationData(csmshr.getFarem(), csmshr.getFaremused(), hasem, fln.getData());
			result.add(new FlowLineNode<CSFlowLineData>(dt, fln.getProbability()));
		}
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}