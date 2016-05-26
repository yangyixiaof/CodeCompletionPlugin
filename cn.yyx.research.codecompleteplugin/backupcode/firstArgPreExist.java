package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSEnterParamInfoData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationData;
import cn.yyx.contentassist.codesynthesis.data.CSPrProperty;
import cn.yyx.contentassist.codesynthesis.data.CSPsProperty;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.CheckUtil;

public class firstArgPreExist extends referedExpression {
	
	List<FlowLineNode<CSFlowLineData>> tmpcache = null;
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		if (realhandler.getSignals().size() != 1)
		{
			throw new CodeSynthesisException("method arg handle signal run into error.");
		}
		Stack<Integer> signals = realhandler.getSignals();
		FlowLineNode<CSFlowLineData> ns = realhandler.getNextstart();
		FlowLineNode<CSFlowLineData> tmp = ns;
		FlowLineNode<CSFlowLineData> mstart = ns;
		FlowLineNode<CSFlowLineData> mstop = null;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSEnterParamInfoData || tmpdata.HasSpecialProperty(CSPsProperty.class) || tmpdata.HasSpecialProperty(CSPrProperty.class) || tmpdata instanceof CSMethodInvocationData)
			{
				Integer preps = signals.peek();
				tmpdata.HandleStackSignal(signals);
				if (preps == DataStructureSignalMetaInfo.MethodInvocation)
				{
					continue;
				}
				if (signals.size() == 1)
				{
					if (tmpdata instanceof CSEnterParamInfoData)
					{
						mstop = tmp;
						realhandler.setNextstart(null);
						realhandler.setMostfar(mstop);
						break;
					}
					else
					{
						throw new CodeSynthesisException("firstArgPreExist does not has Em as very-pre?");
					}
				}
			}
			tmp = tmp.getPrev();
		}
		if (mstart == null || mstop == null || tmp == null)
		{
			throw new CodeSynthesisException("No firstArg start or stop, conflict happens. CSEnterParamInfoData times < 0, conflict happens.");
		}
		mstart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		mstop.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		List<FlowLineNode<CSFlowLineData>> result = CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, realhandler, mstart, mstop);
		tmpcache = result;
		return result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof firstArgPreExist)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof firstArgPreExist)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		if (tmpcache == null)
		{
			tmpcache = HandleCodeSynthesis(squeue, smthandler);
		}
		return CodeSynthesisHelper.HandleInferredField(tmpcache, squeue, smthandler, reservedword, expectedinfer);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		if (tmpcache == null)
		{
			tmpcache = HandleCodeSynthesis(squeue, smthandler);
		}
		return CodeSynthesisHelper.HandleInferredMethodReference(tmpcache, squeue, smthandler, reservedword, expectedinfer);
	}
	
}