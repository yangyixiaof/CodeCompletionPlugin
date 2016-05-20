package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.VirtualCSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
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

	public CodeSynthesisPredictTask(PreTryFlowLineNode<Sentence> pretrylastpara, SynthesisHandler sh, AeroLifeCycle alc,
			CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		this.pretrylast = pretrylastpara;
		this.sh = sh;
		this.alc = alc;
		this.csfl = csfl;
		this.aoi = aoi;
	}

	@Override
	public void run() {
		DoFirstRealCodePredictAndSynthesis(sh, alc, csfl, aoi);
		// normal extend.
		int extendtimes = 1;
		while ((csfl.getValidovers() < PredictMetaInfo.OneCodeSynthesisTaskValidFinalSize)
				&& (extendtimes < PredictMetaInfo.MaxExtendLength)) {
			DoOneRealCodePredictAndSynthesis(alc, csfl, aoi);
		}
	}

	private void DoFirstRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, CodeSynthesisFlowLines csfl,
			ASTOffsetInfo aoi) {
		// first level initial the CodeSynthesisFlowLine.
		csfl.BeginOperation();

		VirtualCSFlowLineQueue vcsdflq = new VirtualCSFlowLineQueue(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(
				-1, null, "", null, false, false, TypeComputationKind.NoOptr, TypeComputationKind.NoOptr, sh), 0));

		FlowLineNode<Sentence> fln = pretrylast;
		List<PredictProbPair> pps = pi.InferNextGeneration(alc, PredictMetaInfo.OneExtendMaxSequence, fln, null);
		HandleExtendOneCodeSynthesis(pps, vcsdflq, fln, csfl, aoi);

		csfl.EndOperation();
	}

	/**
	 * for second and beyond turns.
	 * 
	 * @param alc
	 * @param fls
	 * @param csfl
	 */
	private void DoOneRealCodePredictAndSynthesis(AeroLifeCycle alc, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		csfl.BeginOperation();

		List<FlowLineNode<CSFlowLineData>> tails = csfl.getTails();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tails.iterator();
		List<PredictProbPair> pps = null;
		Sentence presete = null;
		int sparesize = 0;
		int remain = PredictMetaInfo.OneLevelExtendMaxSequence;
		int currsize = ComputePhysicalNodeSizeFromDataNodes(tails) + 1;
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> tail = itr.next();
			if (!tail.isCouldextend()) {
				continue;
			}
			Sentence nowsete = tail.getData().getSete();
			if (nowsete != presete) {
				currsize--;
				int expectsize = Math.min(remain / currsize, PredictMetaInfo.OneExtendMaxSequence);
				expectsize += sparesize;
				int gap = expectsize - PredictMetaInfo.OneExtendMaxSequence;
				sparesize = gap > 0 ? gap : 0;
				expectsize -= sparesize;
				pps = pi.InferNextGeneration(alc, expectsize, tail, pretrylast);
				int realsize = pps.size();
				sparesize = expectsize - realsize;
				remain -= realsize;
			}
			CSFlowLineQueue csdflq = new CSFlowLineQueue(tail);
			HandleExtendOneCodeSynthesis(pps, csdflq, tail, csfl, aoi);
			presete = nowsete;
		}

		csfl.EndOperation();
	}
	
	private int ComputePhysicalNodeSizeFromDataNodes(List<FlowLineNode<CSFlowLineData>> tails)
	{
		int total = 0;
		Sentence presete = null;
		Iterator<FlowLineNode<CSFlowLineData>> itr = tails.iterator();
		while (itr.hasNext()) {
			FlowLineNode<CSFlowLineData> tail = itr.next();
			Sentence nowsete = tail.getData().getSete();
			if (nowsete != presete) {
				total++;
			}
			presete = nowsete;
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	private void HandleExtendOneCodeSynthesis(List<PredictProbPair> pps, CSFlowLineQueue csdflq, FlowLineNode<?> fln,
			CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		Iterator<PredictProbPair> pitr = pps.iterator();
		while (pitr.hasNext()) {
			PredictProbPair ppp = pitr.next();
			Sentence pred = ppp.getPred();
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb(), aoi);
			statement predsmt = pred.getSmt();
			try {
				List<FlowLineNode<CSFlowLineData>> addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				if (addnodes != null && addnodes.size() > 0) {
					Iterator<FlowLineNode<CSFlowLineData>> aitr = addnodes.iterator();
					while (aitr.hasNext()) {
						FlowLineNode<CSFlowLineData> addnode = aitr.next();
						boolean over = false;
						try {
							over = predsmt.HandleOverSignal(new FlowLineStack(addnode));
							addnode.setCouldextend(!over);
						} catch (CodeSynthesisException e) {
							// testing
							System.err.println(
									"Error occurs when doing code synthesis, this predict and the following will be ignored.");
							e.printStackTrace();
							continue;
						}
						if (over) {
							csfl.AddCodeSynthesisOver(addnode, pred);
						} else {
							if (csdflq instanceof VirtualCSFlowLineQueue) {
								// means first infer level.
								csfl.AddToFirstLevel(addnode, (FlowLineNode<Sentence>) fln);
							} else {
								csfl.AddToNextLevel(addnode, (FlowLineNode<CSFlowLineData>) fln);
							}
						}
					}
				}
			} catch (CodeSynthesisException e) {
				// testing
				System.err.println(
						"Error occurs when doing code synthesis, this predict and the following will be ignored.");
				e.printStackTrace();
				continue;
			}
		}
	}

}