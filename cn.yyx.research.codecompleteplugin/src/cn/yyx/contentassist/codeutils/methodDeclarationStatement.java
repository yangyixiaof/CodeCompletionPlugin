package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class methodDeclarationStatement extends statement{
	
	argTypeList typelist = null; // warning: typelist could be null.
	identifier id = null;
	type rt = null;
	
	public methodDeclarationStatement(String smtcode, argTypeList typelist, identifier name, type rt) {
		super(smtcode);
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
			double typelistsim = 0;
			if (typelist == null && ((methodDeclarationStatement)t).typelist == null)
			{
				typelistsim = 1;
			}
			if (typelist != null)
			{
				typelistsim = typelist.Similarity(((methodDeclarationStatement) t).typelist);
			}
			return 0.3 + 0.7*(0.6*(typelistsim) + 0.4*(id.Similarity(((methodDeclarationStatement) t).id)));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("methodDeclaration should not be generated.");
		return null;
		/*List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> mergedls = null;
		if (typelist != null)
		{
			List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
			mergedls = CSFlowLineHelper.ForwardConcate(null, idls, "(", tpls, "){\n\n}", squeue, smthandler, null);
		}
		else
		{
			mergedls = CSFlowLineHelper.ConcateOneFlowLineList(null, idls, "(){\n\n}");
		}
		List<FlowLineNode<CSFlowLineData>> rtls = rt.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ForwardConcate("public ", rtls, " ", mergedls, null, squeue, smthandler, null);*/
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}