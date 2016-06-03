package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.VarArgCCType;

public class lastArgType extends type {
	
	type tp = null;
	// '...'
	
	public lastArgType(type tppara) {
		this.tp = tppara;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof lastArgType)
		{
			if (tp.CouldThoughtSame(((lastArgType) t).tp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lastArgType)
		{
			return 0.4+0.6*(tp.Similarity(((lastArgType) t).tp));
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> ls = CSFlowLineHelper.ConcateOneFlowLineList(null, tpls, "...");
		Iterator<FlowLineNode<CSFlowLineData>> itr = ls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> lli = itr.next();
			CSFlowLineData llidata = lli.getData();
			CCType dcls = llidata.getDcls();
			llidata.setDcls(new VarArgCCType(dcls));
		}
		return ls;
	}

}