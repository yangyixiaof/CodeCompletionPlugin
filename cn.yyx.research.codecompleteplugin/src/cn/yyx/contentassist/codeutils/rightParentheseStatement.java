package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

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
	
	@Override
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
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CSFlowLineBackTraceGenerationHelper.SearchAndModifyLeftParentheseNode(squeue, smthandler, times);
		return null;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return false;
	}
	
}