package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForExpOverData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class forExpOverStatement extends rawForExpOverStatement{
	
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

	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		int sttop = cstack.peek();
		if (sttop == StructureSignalMetaInfo.CommonForInitWaitingOver)
		{
			cstack.pop();
		}
		else
		{
			return true;
		}
		cstack.push(StructureSignalMetaInfo.CommonForExpWaitingOver);
		return false;
	}*/

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.SymbolMark);
		cs.AddOneData(";", null);
		squeue.add(cs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ";");
		Iterator<FlowLineNode<CSFlowLineData>> itr = smtls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> smtln = itr.next();
			CSFlowLineData smtdata = smtln.getData();
			result.add(new FlowLineNode<CSFlowLineData>(new CSForExpOverData(smtdata), smtln.getProbability()));
		}
		// result.add(new FlowLineNode<CSFlowLineData>(new CSForExpOverData(squeue.GenerateNewNodeId(), smthandler.getSete(), ";", null, true, true, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
}