package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSRightParenInfoData;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class rightParentheseStatement extends statement{
	
	int times = 0;
	
	public rightParentheseStatement(int count) {
		this.times = count;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return 1;
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer hint = cstack.peek();
		if (hint == null)
		{
			return true;
		}
		ComplicatedSignal cs = ComplicatedSignal.ParseComplicatedSignal(hint);
		if (cs == null || cs.getSign() == StructureSignalMetaInfo.ParentheseBlock || times > cs.getCount())
		{
			return true;
		}
		int remaincounts = cs.getCount() - times;
		if (remaincounts == 0)
		{
			cstack.pop();
		}
		else
		{
			cs.setCount(remaincounts);
		}
		return false;
	}*/
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		squeue.SetLastHasHole();
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		CSRightParenInfoData cr = new CSRightParenInfoData(times, squeue.GenerateNewNodeId(), smthandler.getSete(), CodeSynthesisHelper.GenerateCopiedContent(times, ")"), null, null, true, TypeComputationKind.NoOptr, squeue.GetLastHandler());
		result.add(new FlowLineNode<CSFlowLineData>(cr, smthandler.getProb()));
		CSFlowLineBackTraceGenerationHelper.SearchAndModifyLeftParentheseNode(squeue, smthandler, cr, times);
		return result;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}