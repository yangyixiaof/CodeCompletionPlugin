package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSEnterParamInfoData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationData;
import cn.yyx.contentassist.codesynthesis.data.CSPrData;
import cn.yyx.contentassist.codesynthesis.data.CSPsData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.CheckUtil;

public class methodArgPreExist extends referedExpression {
	
	List<FlowLineNode<CSFlowLineData>> tmpcache = null;
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		if (realhandler.getSignals().isEmpty())
		{
			throw new CodeSynthesisException("method arg handle signal run into error.");
		}
		FlowLineNode<CSFlowLineData> ns = realhandler.getNextstart();
		FlowLineNode<CSFlowLineData> tmp = ns;
		FlowLineNode<CSFlowLineData> mstart = ns;
		FlowLineNode<CSFlowLineData> mstop = null;
		while (tmp != null)
		{
			// TODO the signal handle of Ps Pr data is wrong.
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSEnterParamInfoData || tmpdata instanceof CSPsData || tmpdata instanceof CSPrData || tmpdata instanceof CSMethodInvocationData)
			{
				mstop = tmp;
				if (tmpdata instanceof CSPsData || tmpdata instanceof CSPrData)
				{
					realhandler.setNextstart(mstop);
					realhandler.setMostfar(mstop.getNext());
				}
				else
				{
					realhandler.setNextstart(mstop.getPrev());
					realhandler.setMostfar(mstop);
				}
				break;
			}
			if (tmpdata instanceof CSMethodInvocationData)
			{
				CSMethodInvocationData csmedt = (CSMethodInvocationData)tmpdata;
				FlowLineNode<CSFlowLineData> mfem = csmedt.getMostfarem();
				if (mfem != null)
				{
					int alltimes = ((CSEnterParamInfoData)mfem.getData()).getTimes();
					int left = alltimes - csmedt.getMostfarused();
					if (left > 0)
					{
						mstop = mfem;
						realhandler.setNextstart(null);
						// mstop.getPrev()
						realhandler.setMostfar(mstop);
						break;
					}
					else
					{
						tmp = mfem;
					}
				}
			}
			tmp = tmp.getPrev();
		}
		if (mstart == null || mstop == null)
		{
			throw new CodeSynthesisException("No firstArg start or stop, conflict happens.");
		}
		mstart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		mstop.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		// int argsize = realhandler.getArgsize();
		return CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, realhandler, mstart, mstop);
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodArgPreExist)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodArgPreExist)
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