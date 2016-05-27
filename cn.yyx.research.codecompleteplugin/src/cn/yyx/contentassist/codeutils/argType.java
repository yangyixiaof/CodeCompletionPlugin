package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class argType implements OneCode{
	
	type tp = null;
	
	public argType(type tp) {
		this.tp = tp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argType)
		{
			if (tp.CouldThoughtSame(((argType) t).tp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argType)
		{
			return 0.3+0.7*(tp.Similarity(((argType) t).tp));
		}
		return 0;
	}

	@Override
	@Deprecated
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("argType should not invoke HandleCodeSynthesis and should invoke HandleArgumentType and .");
		/*List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		if (!(tp instanceof virtualInferredType))
		{
			CodeSynthesisHelper.DirectlyGenerateNameOfType(tpls, squeue, smthandler);
		}
		return tpls;*/
		return null;
	}
	
	/**
	 * needs to generate the variable name.
	 * @param squeue
	 * @param smthandler
	 * @return
	 * @throws CodeSynthesisException
	 */
	public List<FlowLineNode<CSFlowLineData>> HandleArgumentType(CSFlowLineQueue squeue, CSStatementHandler smthandler, char seed)
			throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> tpls = null;
		if (tp instanceof virtualInferredType)
		{
			tpls = ((virtualInferredType) tp).HandleVirtualInferredTypeCodeSynthesis(squeue, smthandler, seed);
		}
		else
		{
			tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			if (tpls == null || tpls.size() == 0)
			{
				return null;
			}
			CodeSynthesisHelper.DirectlyGenerateNameOfType(tpls, squeue, smthandler);
		}
		return tpls;
	}
	
	public char HandleSeed(char seed)
	{
		if (tp instanceof virtualInferredType)
		{
			seed = (char) (seed + 1);
		}
		return seed;
	}
	
}