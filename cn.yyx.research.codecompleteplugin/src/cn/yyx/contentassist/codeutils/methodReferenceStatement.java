package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSMethodReferenceStatementHandler;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class methodReferenceStatement extends expressionStatement{
	
	identifier id = null;
	referedExpression rexp = null;
	
	public methodReferenceStatement(identifier name, referedExpression mrexp) {
		this.id = name;
		this.rexp = mrexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			if (id.CouldThoughtSame(((methodReferenceStatement) t).id) || rexp.CouldThoughtSame(((methodReferenceStatement) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			return 0.3 + 0.7*(0.5*(id.Similarity(((methodReferenceStatement) t).id)) + 0.5*(rexp.Similarity(((methodReferenceStatement) t).rexp)));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode nacs = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, nacs, ai);
		if (conflict)
		{
			return conflict;
		}
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(nacs.GetFirstDataWithoutTypeCheck());
		nai.setDirectlyMemberIsMethod(true);
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		rexp.HandleCodeSynthesis(squeue, expected, handler, fcs, nai);
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		CSMethodReferenceStatementHandler csmrsh = new CSMethodReferenceStatementHandler(idls.get(0).getData().getData(), smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csmrsh);
		if (!(csmrsh.isFieldused()))
		{
			List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(rels, idls, squeue, smthandler, "::");
			if (ls.size() == 0)
			{
				return CSFlowLineHelper.ConcateTwoFlowLineNodeList(null, idls, "::", rels, null, TypeComputationKind.NoOptr, squeue, smthandler, null);
			}
			return ls;
		}
		return rels;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}