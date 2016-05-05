package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class argTypeList implements OneCode {
	
	List<type> tps = new LinkedList<type>();
	
	public argTypeList() {
	}
	
	public void AddToFirst(type re)
	{
		tps.add(0, re);
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argTypeList)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argTypeList)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		Iterator<type> itr = tps.iterator();
		type tp = itr.next();
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleArgumentType(squeue, smthandler, 'A');
		while (itr.hasNext())
		{
			type ntp = itr.next();
			List<FlowLineNode<CSFlowLineData>> ntpls = ntp.HandleCodeSynthesis(squeue, smthandler);
			tpls = CSFlowLineHelper.ForwardMerge(null, tpls, ",", ntpls, null, squeue, smthandler, null, null);
		}
		return tpls;
	}
	
}