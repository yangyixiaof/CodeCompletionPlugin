package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class simpleType extends type{
	
	String text = null;
	
	public simpleType(String text) {
		this.text = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof simpleType)
		{
			double sim = SimilarityHelper.ComputeTwoStringSimilarity(text, ((simpleType) t).text);
			if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof simpleType)
		{
			return SimilarityHelper.ComputeTwoStringSimilarity(text, ((simpleType) t).text);
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		TypeCheck tc = new TypeCheck();
		Class<?> c = null;
		try {
			c = Class.forName(text);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (c == null)
		{
			tc = null;
		}
		else
		{
			tc.setExpreturntype(text);
			tc.setExpreturntypeclass(c);
		}
		result.AddOneData(text, tc);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), text, null, null, false, TypeComputationKind.NoOptr, squeue.GetLastHandler()), smthandler.getProb()));
		List<FlowLineNode<CSFlowLineData>> rtls = CodeSynthesisHelper.HandleRawTypeSpecificationInfer(result, squeue, smthandler);
		if (rtls.size() == 0)
		{
			return result;
		}
		return rtls;
	}
	
}