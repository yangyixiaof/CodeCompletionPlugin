package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class parameterizedType extends type{
	
	identifier id = null;
	typeList typelist = null;
	
	public parameterizedType(identifier id, typeList tlist) {
		this.id = id;
		this.typelist = tlist;
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

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
		if (conflict)
		{
			return true;
		}
		CSNode tcs = new CSNode(CSNodeType.HalfFullExpression);
		conflict = typelist.HandleCodeSynthesis(squeue, expected, handler, tcs, ai);
		if (conflict)
		{
			return true;
		}
		result.AddOneData(cs.GetFirstDataWithoutTypeCheck()+"<"+tcs.GetFirstDataWithoutTypeCheck()+">", null);
		return false;
	}*/
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> modifiedidls = CodeSynthesisHelper.HandleRawTypeSpecificationInfer(idls, squeue, smthandler);
		if (modifiedidls.size() == 0)
		{
			modifiedidls = idls;
		}
		List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateTwoFlowLineNodeList(null, modifiedidls, "<", tpls, ">", TypeComputationKind.NoOptr, squeue, smthandler, null);
	}
	
}