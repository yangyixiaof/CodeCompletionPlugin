package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CodeSynthesisPredictTask implements Runnable {
	
	PreTryFlowLineNode<Sentence> pretrylast = null;
	SynthesisHandler sh = null;
	AeroLifeCycle alc = null;
	CodeSynthesisFlowLines csfl = null;
	ASTOffsetInfo aoi = null;
	PredictInfer pi = new PredictInfer();
	
	public CodeSynthesisPredictTask(PreTryFlowLineNode<Sentence> pretrylastpara, SynthesisHandler sh, AeroLifeCycle alc, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		this.pretrylast = pretrylastpara;
	}
	
	@Override
	public void run() {
		DoFirstRealCodePredictAndSynthesis(sh, alc, csfl, aoi);
		// normal extend.
		int extendtimes = 1;
		while (extendtimes < PredictMetaInfo.MaxExtendLength)
		{
			DoOneRealCodePredictAndSynthesis(alc, csfl, aoi);
		}
	}
	
	private void DoFirstRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		// first level initial the CodeSynthesisFlowLine.
		csfl.BeginOperation();
		
		// VirtualCSFlowLineQueue vcsdflq = new VirtualCSFlowLineQueue(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(-1, null, null, null, false, false, TypeComputationKind.NoOptr, TypeComputationKind.NoOptr, sh), 0));
		FlowLineNode<Sentence> fln = pretrylast;
		List<PredictProbPair> pps = pi.InferNextGeneration(alc, PredictMetaInfo.ExtendFinalMaxSequence, fln, null);
		HandleExtendOneCodeSynthesis(pps, null, fln, csfl, aoi);
		
		csfl.EndOperation();
	}
	
	/**
	 * for second and beyond turns.
	 * @param alc
	 * @param fls
	 * @param csfl
	 */
	private void DoOneRealCodePredictAndSynthesis(AeroLifeCycle alc, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		csfl.BeginOperation();
		
		List<FlowLineNode<CSFlowLineData>> tails = csfl.getTails();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tails.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> tail = itr.next();
			if (!tail.isCouldextend())
			{
				continue;
			}
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(tail, csfl, PredictMetaInfo.NgramMaxSize-1);
			List<PredictProbPair> pps = PredictHelper.PredictSentences(alc, ls, PredictMetaInfo.ExtendFinalMaxSequence);
			CSFlowLineQueue csdflq = new CSFlowLineQueue(tail);
			HandleExtendOneCodeSynthesis(pps, csdflq, tail, csfl, aoi);
		}
		
		csfl.EndOperation();
	}
	
	@SuppressWarnings("unchecked")
	private void HandleExtendOneCodeSynthesis(List<PredictProbPair> pps, CSFlowLineQueue csdflq, FlowLineNode<?> fln, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi)
	{
		// TODO does VirtualCSFlowLineQueue handled? csdflq could be null, if so, this is the first generation.
		Iterator<PredictProbPair> pitr = pps.iterator();
		while (pitr.hasNext())
		{
			PredictProbPair ppp = pitr.next();
			Sentence pred = ppp.getPred();
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb(), aoi);
			statement predsmt = pred.getSmt();
			try {
				List<FlowLineNode<CSFlowLineData>> addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				if (addnodes != null && addnodes.size() > 0)
				{
					Iterator<FlowLineNode<CSFlowLineData>> aitr = addnodes.iterator();
					while (aitr.hasNext())
					{
						FlowLineNode<CSFlowLineData> addnode = aitr.next();
						try
						{
							boolean over = predsmt.HandleOverSignal(new FlowLineStack(addnode));
							addnode.setCouldextend(!over);
						}  catch (CodeSynthesisException e) {
							// testing
							System.err.println("Error occurs when doing code synthesis, this predict and the following will be ignored.");
							e.printStackTrace();
							continue;
						}
						if (fln != null)
						{
							csfl.AddToFirstLevel(addnode, (FlowLineNode<Sentence>) fln);
						}
						else
						{
							csfl.AddToNextLevel(addnode, (FlowLineNode<CSFlowLineData>) fln);
						}
					}
				}
			} catch (CodeSynthesisException e) {
				// testing
				System.err.println("Error occurs when doing code synthesis, this predict and the following will be ignored.");
				e.printStackTrace();
				continue;
			}
		}
	}
	
}