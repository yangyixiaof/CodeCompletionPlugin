package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class typeList implements OneCode {
	
	// reminder not to add '(' ')' to left and right.
	private List<type> tps = new LinkedList<type>();
	
	public typeList() {
	}
	
	public void AddToFirst(type re)
	{
		tps.add(0, re);
	}
	
	public void AddReferedExpression(type re)
	{
		tps.add(re);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeList)
		{
			return SimilarityHelper.CouldThoughtListsOfTypeSame(tps, ((typeList) t).tps);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeList)
		{
			return SimilarityHelper.ComputeListsOfTypeSimilarity(tps, ((typeList) t).tps);
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		StringBuilder sb = new StringBuilder();
		Iterator<type> itr = tps.iterator();
		while (itr.hasNext())
		{
			type tp = itr.next();
			CSNode cs = new CSNode(CSNodeType.TempUsed);
			boolean conflict = tp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			sb.append(cs.GetFirstDataWithoutTypeCheck());
			if (itr.hasNext())
			{
				sb.append(',');
			}
		}
		CSNode fcs = new CSNode(CSNodeType.HalfFullExpression);
		fcs.AddOneData(sb.toString(), null);
		return false;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		Iterator<type> itr = tps.iterator();
		type tp = itr.next();
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		while (itr.hasNext())
		{
			type ntp = itr.next();
			List<FlowLineNode<CSFlowLineData>> ntpls = ntp.HandleCodeSynthesis(squeue, smthandler);
			tpls = CSFlowLineHelper.ConcateTwoFlowLineNodeList(prefix, flnsone, concator, flnstwo, postfix, tck, squeue, smthandler, structsignal);
		}
		return null;
	}
}
