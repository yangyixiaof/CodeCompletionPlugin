package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArrayAccessEndProperty;
import cn.yyx.contentassist.codesynthesis.data.CSArrayAccessStartProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.StringUtil;

public class arrayAccessEndStatement extends statement{
	
	expressionStatement es = null;
	int endrtimes = -1;
	
	public arrayAccessEndStatement(String smtcode, expressionStatement es, int endrtimes) {
		super(smtcode);
		this.es = es;
		this.endrtimes = endrtimes;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayAccessEndStatement)
		{
			return es.CouldThoughtSame(((arrayAccessEndStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayAccessEndStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((arrayAccessEndStatement) t).es));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		
		String endrcode = StringUtil.GenerateDuplicates("]", endrtimes);
		List<FlowLineNode<CSFlowLineData>> esls = es.HandleCodeSynthesis(squeue, smthandler);
		esls = CSFlowLineHelper.ConcateOneFlowLineList(null, esls, endrcode);
		
		if (esls == null || esls.size() == 0)
		{
			return null;
		}
		
		FlowLineNode<CSFlowLineData> cnode = null;
		int ertime = endrtimes;
		if (es instanceof arrayAccessStatement)
		{
			if (endrtimes > 1)
			{
				ertime = endrtimes-1;
			}
		}
		//else
		//{
		//	CSArrayAccessEndData caaed = new CSArrayAccessEndData(endrtimes, squeue.GenerateNewNodeId(), smthandler.getSete(), endrcode, null, true, false, null, null, squeue.GetLastHandler());
		//	FlowLineNode<CSFlowLineData> fln = new FlowLineNode<CSFlowLineData>(caaed, smthandler.getProb());
		//	Stack<Integer> signals = new Stack<Integer>();
		//	caaed.HandleStackSignal(signals);
		//	FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSArrayAccessStartData.class, signals);
		//	if (cnode == null)
		//	{
		//		throw new CodeSynthesisException("ArrayAccessBlcok disappeared.");
		//	}
		//	CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
		//	result.add(fln);
		//}
		if (ertime > 0)
		{
			Stack<Integer> signals = new Stack<Integer>();
			signals.push(ComplicatedSignal.GenerateComplicatedSignal(DataStructureSignalMetaInfo.ArrayAccessBlcok, ertime));
			cnode = squeue.BackSearchForTheNextOfSpecialClass(CSArrayAccessStartProperty.class, signals);
			if (cnode != null)
			{
				FlowLineNode<CSFlowLineData> aastart = cnode.getPrev();
				Iterator<FlowLineNode<CSFlowLineData>> itr = esls.iterator();
				while (itr.hasNext())
				{
					FlowLineNode<CSFlowLineData> fln = itr.next();
					List<FlowLineNode<CSFlowLineData>> gks = CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
					if (gks != null && gks.size() > 0)
					{
						FlowLineNode<CSFlowLineData> gn = gks.get(0);
						CCType icls = gn.getData().getDcls();
						if (icls != null && (icls.getCls() == int.class || icls.getCls() == Integer.class))
						{
							CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, aastart);
							fln.getData().setCsep(new CSArrayAccessEndProperty(ertime, null));
							result.add(fln);
							// result.add(new FlowLineNode<CSFlowLineData>(new CSArrayAccessEndData(ertime, fln.getData()), fln.getProbability()));
						}
					}
				}
			}
		} else {
			return esls;
		}
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}