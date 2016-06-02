package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForExpOverProperty;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

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
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		
		// debugging code, do not remove.
		if (smtls == null)
		{
			System.err.println("smtls is null.");
		}
		
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ";");
		Iterator<FlowLineNode<CSFlowLineData>> itr = smtls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> smtln = itr.next();
			CSFlowLineData smtdata = smtln.getData();
			smtdata.setCsep(new CSForExpOverProperty(null));
		}
		// result.add(new FlowLineNode<CSFlowLineData>(new CSForExpOverData(squeue.GenerateNewNodeId(), smthandler.getSete(), ";", null, true, true, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return smtls;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}