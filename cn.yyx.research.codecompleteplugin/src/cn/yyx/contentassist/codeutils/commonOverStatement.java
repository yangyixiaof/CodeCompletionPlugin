package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSCommonOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.ListHelper;

public class commonOverStatement extends statement implements SWrapper{
	
	statement smt = null;
	
	public commonOverStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ";");
		if (smtls == null)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> ritr = smtls.iterator();
		while (ritr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = ritr.next();
			// result.addAll(CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, null));
			List<FlowLineNode<CSFlowLineData>> rls = CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, null);
			if (rls != null && rls.size() > 0)
			{
				fln.setSynthesisdata(rls.get(0).getData());
			}
		}
		ListHelper.AddExtraPropertyToAllListNodes(smtls, new CSCommonOverProperty(null)); // result
		return smtls;
		// return result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonOverStatement)
		{
			return smt.CouldThoughtSame(((commonOverStatement) t).smt);
		}
		if (t instanceof statement)
		{
			return smt.CouldThoughtSame(t);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonOverStatement)
		{
			return smt.Similarity(((commonOverStatement) t).smt);
		}
		if (t instanceof statement)
		{
			return smt.Similarity(t);
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}