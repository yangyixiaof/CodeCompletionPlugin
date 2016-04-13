package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

public class wildCardType extends type{
	
	boolean extended = false;
	type tp = null;
	
	public wildCardType(boolean extended, type tp) {
		this.extended = extended;
		this.tp = tp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof wildCardType)
		{
			if (tp == null)
			{
				return true;
			}
			else
			{
				if (tp.CouldThoughtSame(((wildCardType) t).tp))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof wildCardType)
		{
			if (tp == null)
			{
				return 1;
			}
			else
			{
				
				return 0.4 + 0.6*(tp.Similarity(((wildCardType) t).tp));
			}
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		if (tp != null)
		{
			CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
			boolean conflict = tp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			String ex = "super";
			TypeCheck tc = null;
			if (extended)
			{
				ex = "extends";
				tc = cs.GetFirstTypeCheck();
			}
			result.AddOneData("?" + " " + ex + " " + cs.GetFirstDataWithoutTypeCheck(), tc);
		}
		else
		{
			result.AddOneData("?", null);
		}
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (tp != null)
		{
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ConcateOneFlowLineList("?" + (extended ? " extends " : " super "), tpls, null);
		}
		else
		{
			List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "?", null, null, false, TypeComputationKind.NoOptr, squeue.GetLastHandler()), smthandler.getProb()));
			return result;
		}
	}
	
}