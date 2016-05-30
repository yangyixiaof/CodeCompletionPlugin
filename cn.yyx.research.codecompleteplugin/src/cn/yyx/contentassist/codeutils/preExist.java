package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSEnterParamInfoProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSMethodInvocationProperty;
import cn.yyx.contentassist.codesynthesis.data.CSPrProperty;
import cn.yyx.contentassist.codesynthesis.data.CSPsProperty;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSInnerLevelPreHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementFirstArgHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;

public class preExist extends referedExpression{
	
	List<FlowLineNode<CSFlowLineData>> tmpcache = null;
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof preExist)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof preExist)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (smthandler instanceof CSInnerLevelPreHandler)
		{
			return InnerLevelPreHandleCodeSynthesis(squeue, smthandler);
		}
		if (smthandler instanceof CSMethodStatementFirstArgHandler)
		{
			return FirstArgPreExistHandleCodeSynthesis(squeue, smthandler);
		}
		if (smthandler instanceof CSMethodStatementHandler)
		{
			return MethodArgPreExistHandleCodeSynthesis(squeue, smthandler);
		}
		return CommonPreExistHandleCodeSynthesis(squeue, smthandler);
	}
	
	public List<FlowLineNode<CSFlowLineData>> InnerLevelPreHandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> result = CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, smthandler, squeue.getLast(), null);
		return result;
	}
	
	public List<FlowLineNode<CSFlowLineData>> CommonPreExistHandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// no problem.
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		FlowLineNode<CSFlowLineData> last = squeue.getLast();
		FlowLineNode<CSFlowLineData> end = CSFlowLineBackTraceGenerationHelper.SearchForWholeNode(last);
		FlowLineNode<CSFlowLineData> efln = null;
		CSFlowLineData csfd = null;
		String nid = null;
		if (end == last) {
			String lid = end.getData().getId();
			efln = last;
			csfd = new CSFlowLineData(squeue.GenerateNewNodeId()+"", efln.getData());
			nid = lid + "." + csfd.getId();
			// result.add(last);
		} else {
			String lid = CSFlowLineBackTraceGenerationHelper.GetConcateId(last, end);
			efln = end.getData().getSynthesisCodeManager().GetSynthesisCodeByKey(lid);
			csfd = new CSFlowLineData(squeue.GenerateNewNodeId()+"", efln.getData());
			nid = lid + "." + csfd.getId();
		}
		csfd.getSynthesisCodeManager().setBlockstart(end, nid);
		FlowLineNode<CSFlowLineData> nfln = new FlowLineNode<CSFlowLineData>(csfd, efln.getProbability());
		end.getData().getSynthesisCodeManager().AddSynthesisCode(nid, nfln);
		result.add(nfln);
		// FlowLineNode<CSFlowLineData> wo = CSFlowLineBackTraceGenerationHelper.GetWholeNodeCode(squeue.getLast());
		// result.add(wo);
		return result;
	}
	
	public List<FlowLineNode<CSFlowLineData>> FirstArgPreExistHandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
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
		if (!(tmp.getData().HasSpecialProperty(CSPrProperty.class)))
		{
			throw new CodeSynthesisException("method synthesis run into error, this node should be pr but not.");
		}
		tmp = tmp.getPrev();
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			FlowLineNode<CSFlowLineData> tmpblockstart = tmpdata.getSynthesisCodeManager().getBlockstart();
			if (tmpblockstart != null)
			{
				tmp = tmpblockstart.getPrev();
				continue;
			}
			
			if (tmpdata.HasSpecialProperty(CSEnterParamInfoProperty.class) || tmpdata.HasSpecialProperty(CSPsProperty.class) || tmpdata.HasSpecialProperty(CSPrProperty.class) || tmpdata.HasSpecialProperty(CSMethodInvocationProperty.class))
			{
				tmpdata.HandleStackSignal(signals);
				if (ClassInstanceOfUtil.ObjectInstanceOf(tmpdata, CSEnterParamInfoProperty.class) && signals.size() == 0)
				{
					mstop = tmp;
					realhandler.setNextstart(null);
					realhandler.setMostfar(mstop);
					break;
				}
			}
			tmp = tmp.getPrev();
		}
		if (mstart == null || mstop == null) //  || tmp == null
		{
			throw new CodeSynthesisException("No firstArg start or stop, conflict happens. CSEnterParamInfoData times < 0, conflict happens.");
		}
		// mstart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		// mstop.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		List<FlowLineNode<CSFlowLineData>> result = CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, realhandler, mstart, mstop);
		tmpcache = result;
		return result;
	}
	
	public List<FlowLineNode<CSFlowLineData>> MethodArgPreExistHandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
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
		FlowLineNode<CSFlowLineData> tmpnext = tmp;
		tmp.getData().HandleStackSignal(signals);
		tmp = tmp.getPrev();
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			FlowLineNode<CSFlowLineData> tmpblockstart = tmpdata.getSynthesisCodeManager().getBlockstart();
			if (tmpblockstart != null)
			{
				tmpnext = tmpblockstart;
				tmp = tmpblockstart.getPrev();
				continue;
			}
			
			if (ClassInstanceOfUtil.ObjectInstanceOf(tmpdata, CSEnterParamInfoProperty.class) || tmpdata.HasSpecialProperty(CSPsProperty.class) || tmpdata.HasSpecialProperty(CSPrProperty.class))
			{
				tmpdata.HandleStackSignal(signals);
				
				if (ClassInstanceOfUtil.ObjectInstanceOf(tmpdata, CSEnterParamInfoProperty.class) && signals.size() == 0)
				{
					mstop = tmp;
					realhandler.setNextstart(null);
					realhandler.setMostfar(mstop);
					break;
				}
				
				if ((tmpdata.HasSpecialProperty(CSPsProperty.class) || tmpdata.HasSpecialProperty(CSPrProperty.class)) && signals.size() == 1)
				{
					mstop = tmpnext;
					realhandler.setNextstart(tmp);
					realhandler.setMostfar(mstop);
					break;
				}
			}
			/*if (ClassInstanceOfUtil.ObjectInstanceOf(tmpdata, CSEnterParamInfoData.class) || tmpdata.HasSpecialProperty(CSPsProperty.class) || tmpdata.HasSpecialProperty(CSPrProperty.class))
			{   //  || ClassInstanceOfUtil.ObjectInstanceOf(tmpdata, CSMethodInvocationData.class)
				// Integer preps = signals.peek();
				tmpdata.HandleStackSignal(signals);
				//if (preps == DataStructureSignalMetaInfo.MethodInvocation)
				//{
				//	continue;
				//}
				if ((!(tmpdata instanceof CSMethodInvocationData)) && signals.size() == 1)
				{
					mstop = tmpnext;
					if (mstop == null)
					{
						// only one. tmpnext is not set yet.
						mstop = tmp;
					}
					realhandler.setNextstart(mstop.getPrev());
					if (tmpdata instanceof CSEnterParamInfoData)
					{
						mstop = tmp;
						realhandler.setNextstart(null);
						realhandler.setMostfar(mstop);
					}
					break;
				}
			}*/
			tmpnext = tmp;
			tmp = tmp.getPrev();
		}
		if (mstart == null || mstop == null)
		{
			throw new CodeSynthesisException("No firstArg start or stop, conflict happens.");
		}
		// mstart.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		// mstop.getData().getSynthesisCodeManager().SetBlockStartToInternNode();
		List<FlowLineNode<CSFlowLineData>> result = CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, realhandler, mstart, mstop);
		tmpcache = result;
		return result;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		// return CodeSynthesisHelper.HandleInferredField(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
		if (tmpcache == null)
		{
			tmpcache = HandleCodeSynthesis(squeue, smthandler);
		}
		if (tmpcache == null || tmpcache.size() == 0)
		{
			return null;
		}
		return CodeSynthesisHelper.HandleInferredField(tmpcache, squeue, smthandler, reservedword, expectedinfer);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		// return CodeSynthesisHelper.HandleInferredMethodReference(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
		if (tmpcache == null)
		{
			tmpcache = HandleCodeSynthesis(squeue, smthandler);
		}
		if (tmpcache == null || tmpcache.size() == 0)
		{
			return null;
		}
		return CodeSynthesisHelper.HandleInferredMethodReference(tmpcache, squeue, smthandler, reservedword, expectedinfer);
	}
	
}