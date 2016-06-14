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
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class identifier extends referedExpression{
	
	private String value = null;
	
	public identifier(String idpara) {
		this.setValue(idpara);
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), value, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof identifier)
		{
			double sim = SimilarityHelper.ComputeTwoStringSimilarity(value, ((identifier) t).value);
			return sim >= PredictMetaInfo.TwoStringSimilarThreshold;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof identifier)
		{
			return SimilarityHelper.ComputeTwoStringSimilarity(value, ((identifier) t).value);
		}
		return 0;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredField(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredMethodReference(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}
	
}