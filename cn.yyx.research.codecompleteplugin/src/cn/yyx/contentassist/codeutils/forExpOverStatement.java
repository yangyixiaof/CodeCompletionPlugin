package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForExpOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSForIniOverProperty;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.BackSearchResult;
import cn.yyx.contentassist.commonutils.ListHelper;

public class forExpOverStatement extends rawForExpOverStatement implements SWrapper{
	
	statement smt = null;
	
	public forExpOverStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forExpOverStatement)
		{
			return smt.CouldThoughtSame(((forExpOverStatement)t).smt);
		}
		if (t instanceof rawForExpOverStatement)
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
		if (t instanceof forExpOverStatement)
		{
			return smt.Similarity(((forExpOverStatement)t).smt);
		}
		if (t instanceof rawForExpOverStatement)
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
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.CommonForExpWaitingOver);
		BackSearchResult br = squeue.BackSearchForTheNextOfSpecialClass(CSForIniOverProperty.class, signals);
		if (!br.isValid())
		{
			throw new CodeSynthesisException("no ini over before exp over?");
		}
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		
		// debugging code, do not remove.
		if (smtls == null)
		{
			System.err.println("smtls is null.");
			return null;
		}
		
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ";");
		Iterator<FlowLineNode<CSFlowLineData>> itr = smtls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> smtln = itr.next();
			if (!br.isSelfisneeded())
			{
				CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, smtln, br.getCnode());
			}
		}
		ListHelper.AddExtraPropertyToAllListNodes(smtls, new CSForExpOverProperty(null));
		// result.add(new FlowLineNode<CSFlowLineData>(new CSForExpOverData(squeue.GenerateNewNodeId(), smthandler.getSete(), ";", null, true, true, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return smtls;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}