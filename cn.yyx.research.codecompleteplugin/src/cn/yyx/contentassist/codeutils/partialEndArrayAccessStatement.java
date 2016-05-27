package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArrayAccessEndData;
import cn.yyx.contentassist.codesynthesis.data.CSArrayAccessStartData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.StringUtil;

public class partialEndArrayAccessStatement extends statement{
	
	expressionStatement es = null;
	int endrtimes = -1;
	
	public partialEndArrayAccessStatement(String smtcode, expressionStatement es, int endrtimes) {
		super(smtcode);
		this.es = es;
		this.endrtimes = endrtimes;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return es.CouldThoughtSame(((partialEndArrayAccessStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((partialEndArrayAccessStatement) t).es));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		
		String endrcode = StringUtil.GenerateDuplicates("]", endrtimes);
		List<FlowLineNode<CSFlowLineData>> esls = es.HandleCodeSynthesis(squeue, smthandler);
		CSFlowLineHelper.ConcateOneFlowLineList(null, esls, endrcode);
		
		if (es instanceof arrayAccessStatement)
		{
			FlowLineNode<CSFlowLineData> cnode = null;
			if (endrtimes > 1)
			{
				Stack<Integer> signals = new Stack<Integer>();
				signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.ArrayAccessBlcok, endrtimes-1));
				cnode = squeue.BackSearchForSpecialClass(CSArrayAccessStartData.class, signals);
			}
			if (cnode != null)
			{
				Iterator<FlowLineNode<CSFlowLineData>> itr = esls.iterator();
				while (itr.hasNext())
				{
					FlowLineNode<CSFlowLineData> fln = itr.next();
					CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
					result.add(fln);
				}
			}
		}
		else
		{
			CSArrayAccessEndData caaed = new CSArrayAccessEndData(endrtimes, squeue.GenerateNewNodeId(), smthandler.getSete(), endrcode, null, true, false, null, null, squeue.GetLastHandler());
			FlowLineNode<CSFlowLineData> fln = new FlowLineNode<CSFlowLineData>(caaed, smthandler.getProb());
			Stack<Integer> signals = new Stack<Integer>();
			caaed.HandleStackSignal(signals);
			FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSArrayAccessStartData.class, signals);
			if (cnode == null)
			{
				throw new CodeSynthesisException("ArrayAccessBlcok disappeared.");
			}
			CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
			result.add(fln);
		}
		
		return esls;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}