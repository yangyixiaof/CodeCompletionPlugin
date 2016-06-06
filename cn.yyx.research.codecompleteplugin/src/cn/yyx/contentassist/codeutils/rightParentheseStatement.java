package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSLeftParenInfoProperty;
import cn.yyx.contentassist.codesynthesis.data.CSRightParenInfoProperty;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.DirectUseFirstOneSide;
import cn.yyx.research.language.Utility.StringUtil;

public class rightParentheseStatement extends statement{
	
	int times = 0;
	
	public rightParentheseStatement(String smtcode, int count) {
		super(smtcode);
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
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		/*List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		CSFlowLineData cr = new CSFlowLineData(squeue.GenerateNewNodeId() + "", smthandler.getSete(), StringUtil.GenerateDuplicates(")", times), null, new DirectUseFirstOneSide(), squeue.GetLastHandler(), new CSRightParenInfoProperty(times, null));
		FlowLineNode<CSFlowLineData> fln = new FlowLineNode<CSFlowLineData>(cr, smthandler.getProb());
		result.add(fln);
		Stack<Integer> signals = new Stack<Integer>();
		cr.HandleStackSignal(signals);
		FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSLeftParenInfoProperty.class, signals);
		CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
		// CSFlowLineBackTraceGenerationHelper.SearchAndModifyLeftParentheseNode(squeue, smthandler, cr, times);
		return result;*/
		
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		CSFlowLineData cr = new CSFlowLineData(squeue.GenerateNewNodeId() + "", smthandler.getSete(), StringUtil.GenerateDuplicates(")", times), null, new DirectUseFirstOneSide(), squeue.GetLastHandler(), new CSRightParenInfoProperty(times, null));
		FlowLineNode<CSFlowLineData> fln = new FlowLineNode<CSFlowLineData>(cr, smthandler.getProb());
		result.add(fln);
		Stack<Integer> signals = new Stack<Integer>();
		cr.HandleStackSignal(signals);
		FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSLeftParenInfoProperty.class, signals);
		List<FlowLineNode<CSFlowLineData>> rls = CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
		if (rls != null && rls.size() > 0)
		{
			fln.setSynthesisdata(rls.get(0).getData());
		}
		return result;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}