package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class constructionDeclarationStatement extends statement {
	
	argTypeList typelist = null; // warning: typelist could be null.
	identifier id = null;
	
	public constructionDeclarationStatement(argTypeList typelist, identifier name) {
		this.typelist = typelist;
		this.id = name;
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
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> mergedls = null;
		if (typelist != null)
		{
			List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
			mergedls = CSFlowLineHelper.ForwardMerge(null, idls, "(", tpls, "){\n\n}", squeue, smthandler, null, null);
		}
		else
		{
			mergedls = CSFlowLineHelper.ConcateOneFlowLineList(null, idls, "(){\n\n}");
		}
		return CSFlowLineHelper.ConcateOneFlowLineList("public ", mergedls, null);
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}