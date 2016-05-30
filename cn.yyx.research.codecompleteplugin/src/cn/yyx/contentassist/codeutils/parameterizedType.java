package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.DirectUseFirstOneSide;

public class parameterizedType extends type{
	
	identifier id = null;
	typeArguments tas = null;
	
	public parameterizedType(identifier id, typeArguments tas) {
		this.id = id;
		this.tas = tas;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof parameterizedType)
		{
			if (id.CouldThoughtSame(((parameterizedType) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof parameterizedType)
		{
			return 0.4 + 0.6*(id.Similarity(((parameterizedType) t).id));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> modifiedidls = CodeSynthesisHelper.HandleRawTypeSpecificationInfer(idls, squeue, smthandler);
		if (modifiedidls == null || modifiedidls.size() == 0)
		{
			modifiedidls = idls;
		}
		List<FlowLineNode<CSFlowLineData>> tpls = tas.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> fls = CSFlowLineHelper.ForwardConcate(null, modifiedidls, "<", tpls, ">", squeue, smthandler, new DirectUseFirstOneSide());
		Iterator<FlowLineNode<CSFlowLineData>> itr = fls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			String tps = fln.getData().getData();
			fln.getData().getDcls().setClstr(tps);
		}
		return fls;
	}
	
}