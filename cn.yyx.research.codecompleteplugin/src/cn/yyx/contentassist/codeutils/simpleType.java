package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
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
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "Object", new CCType(Object.class, "Object"), null, squeue.GetLastHandler()), smthandler.getProb()));
		List<FlowLineNode<CSFlowLineData>> idls = new LinkedList<FlowLineNode<CSFlowLineData>>();
		idls.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), text, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		List<FlowLineNode<CSFlowLineData>> stps = CodeSynthesisHelper.HandleRawTypeSpecificationInfer(idls, squeue, smthandler);
		// result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), text, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		// List<FlowLineNode<CSFlowLineData>> rtls = CodeSynthesisHelper.HandleRawTypeSpecificationInfer(result, squeue, smthandler);
		if (stps == null || stps.size() == 0)
		{
			return result;
		}
		return stps;
	}
	
}