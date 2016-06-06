package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForUpdOverProperty;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.ListHelper;

public class forUpdOverStatement extends rawForUpdOverStatement implements SWrapper{
	
	statement smt = null;
	
	public forUpdOverStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forUpdOverStatement)
		{
			return smt.CouldThoughtSame(((forUpdOverStatement)t).smt);
		}
		if (t instanceof rawForUpdOverStatement)
		{
			return true;
		}
		if (t instanceof statement)
		{
			return smt.CouldThoughtSame(t);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forUpdOverStatement)
		{
			return smt.Similarity(((forUpdOverStatement)t).smt);
		}
		if (t instanceof rawForUpdOverStatement)
		{
			return 0.5;
		}
		if (t instanceof statement)
		{
			return smt.Similarity(t);
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ") " + CodeSynthesisHelper.GenerateBlockCode(smthandler));
		if (smtls == null || smtls.size() == 0)
		{
			return null;
		}
		/*Iterator<FlowLineNode<CSFlowLineData>> smtitr = smtls.iterator();
		while (smtitr.hasNext())
		{
			FlowLineNode<CSFlowLineData> smtln = smtitr.next();
			CSFlowLineData smtdata = smtln.getData();
			smtdata.setCsep(new CSForUpdOverProperty(null));
		}*/
		ListHelper.AddExtraPropertyToAllListNodes(smtls, new CSForUpdOverProperty(null));
		Iterator<FlowLineNode<CSFlowLineData>> ritr = smtls.iterator();
		while (ritr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = ritr.next();
			List<FlowLineNode<CSFlowLineData>> rls = CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, null);
			if (rls != null && rls.size() > 0)
			{
				fln.setSynthesisdata(rls.get(0).getData());
			}
			// result.addAll(CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, null));
		}
		return smtls;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}