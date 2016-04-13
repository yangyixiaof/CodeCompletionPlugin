package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSEnterParamInfoData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSPrData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;

public class firstArgPreExist extends referedExpression{

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		FlowLineNode<CSFlowLineData> ns = realhandler.getNextstart();
		FlowLineNode<CSFlowLineData> tmp = ns;
		FlowLineNode<CSFlowLineData> mstart = null;
		FlowLineNode<CSFlowLineData> mstop = null;
		// FlowLineNode<CSFlowLineData> tmppre = null;
		int waittoconsumedpr = 0;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSPrData)
			{
				mstart = tmp;
				waittoconsumedpr++;
			}
			if (tmpdata instanceof CSEnterParamInfoData)
			{
				realhandler.setMostfar(tmp);
				// mstop = tmppre;
				mstop = tmp;
				CSEnterParamInfoData ce = (CSEnterParamInfoData) tmpdata;
				int alltimes = ce.getTimes();
				int allremain = Math.max(0, alltimes - waittoconsumedpr);
				int consumed = alltimes - allremain;
				waittoconsumedpr -= consumed;
				if (waittoconsumedpr == 0)
				{
					break;
				}
			}
			// tmppre = tmp;
			tmp = tmp.getPrev();
		}
		if (mstart == null || mstop == null || tmp == null)
		{
			throw new CodeSynthesisException("No firstArg start or stop, conflict happens. CSEnterParamInfoData times < 0, conflict happens.");
		}
		mstart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		mstop.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		// int argsize = realhandler.getArgsize();
		return CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, realhandler, mstart, mstop);
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
	
}