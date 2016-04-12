package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class methodDeclarationStatement extends statement{
	
	argTypeList typelist = null;
	identifier id = null;
	type rt = null;
	
	public methodDeclarationStatement(argTypeList typelist, identifier name, type rt) {
		this.typelist = typelist;
		this.id = name;
		this.rt = rt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			if (typelist.CouldThoughtSame(((methodDeclarationStatement) t).typelist) || id.CouldThoughtSame(((methodDeclarationStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			return 0.3 + 0.7*(0.6*(typelist.Similarity(((methodDeclarationStatement) t).typelist)) + 0.4*(id.Similarity(((methodDeclarationStatement) t).id)));
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
			return true;
		}
		CSNode tpscs = new CSNode(CSNodeType.ReferedExpression);
		conflict = typelist.HandleCodeSynthesis(squeue, expected, handler, tpscs, ai);
		if (conflict)
		{
			return true;
		}
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		fcs.AddOneData(nacs.GetFirstDataWithoutTypeCheck() + tpscs.GetFirstDataWithoutTypeCheck(), null);
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> mergedls = null;
		if (typelist != null)
		{
			List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
			mergedls = CSFlowLineHelper.ConcateTwoFlowLineNodeList(null, idls, "(", tpls, "){\n\n}", TypeComputationKind.NoOptr, squeue, smthandler, null);
		}
		else
		{
			mergedls = CSFlowLineHelper.ConcateOneFLStamp(null, idls, "(){\n\n}");
		}
		List<FlowLineNode<CSFlowLineData>> rtls = rt.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateTwoFlowLineNodeList("public ", rtls, " ", mergedls, null, TypeComputationKind.NoOptr, squeue, smthandler, null);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}