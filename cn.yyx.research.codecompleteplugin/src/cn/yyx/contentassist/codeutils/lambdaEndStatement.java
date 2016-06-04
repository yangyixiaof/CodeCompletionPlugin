package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaData;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaEndProperty;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.ListHelper;

public class lambdaEndStatement extends statement implements SWrapper{
	
	statement smt = null;
	
	public lambdaEndStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		if (smtls == null)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> ritr = smtls.iterator();
		while (ritr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = ritr.next();
			Stack<Integer> signals = new Stack<Integer>();
			signals.push(DataStructureSignalMetaInfo.LambdaExpression);
			fln.getData().HandleStackSignal(signals);
			FlowLineNode<CSFlowLineData> cnode = squeue.BackSearchForSpecialClass(CSLambdaData.class, signals);
			if (cnode == null)
			{
				throw new CodeSynthesisException("lambda block check runs into error.");
			}
			CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, fln, cnode);
			// result.addAll();
		}
		ListHelper.AddExtraPropertyToAllListNodes(smtls, new CSLambdaEndProperty(null));
		return smtls;
		// ListHelper.AddExtraPropertyToAllListNodes(result, new CSLambdaEndProperty(null));
		// return result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		/*if (t instanceof lambdaEndStatement)
		{
			return smt.CouldThoughtSame(((lambdaEndStatement) t).smt);
		}*/
		if (t instanceof SWrapper)
		{
			return this.CouldThoughtSame(((SWrapper) t).GetContent());
		}
		return smt.CouldThoughtSame(t);
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lambdaEndStatement)
		{
			return smt.Similarity(((lambdaEndStatement) t).smt);
		}
		if (t instanceof SWrapper)
		{
			return this.Similarity(((SWrapper) t).GetContent());
		}
		return smt.Similarity(t);
		// return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		/*Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.LambdaExpression);
		FlowLineNode<CSFlowLineData> cnode = cstack.BackSearchForFirstSpecialClass(CSLambdaData.class, signals);
		if (cnode == null)
		{
			throw new CodeSynthesisException("lambda exoression run into block error.");
		}*/
		return false;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}