package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.aerohandle.PredictInfer;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.VirtualCSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CodeSynthesisPredictTask implements Runnable {

	PreTryFlowLineNode<Sentence> pretrylast = null;
	String pretrylastwholetrace = null;
	SynthesisHandler sh = null;
	AeroLifeCycle alc = null;
	CodeSynthesisFlowLines csfl = null;
	ASTOffsetInfo aoi = null;
	PredictInfer pi = null;
	int totalsuccess = 0;
	int totalstep = 0;

	public CodeSynthesisPredictTask(PreTryFlowLineNode<Sentence> pretrylastpara, SynthesisHandler sh, AeroLifeCycle alc,
			CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi, int id) {
		this.pretrylast = pretrylastpara;
		this.pretrylastwholetrace = PrintWholeTrace(pretrylastpara, "");
		this.sh = sh;
		this.alc = alc;
		this.csfl = csfl;
		this.aoi = aoi;
		this.pi = new PredictInfer(id);
	}

	@Override
	public void run() {
		RecursiveCodePredictAndSynthesis(0, null, false, null);
	}
	
	@SuppressWarnings("unchecked")
	private List<PredictProbPair> RecursiveCodePredictAndSynthesis(int level, FlowLineNode<CSFlowLineData> start, boolean hassilb, List<PredictProbPair> lastsilbppps)
	{
		if (level >= PredictMetaInfo.MaxExtendLength)
		{
			return null;
		}
		if (TotalStopCondition())
		{
			return null;
		}
		List<PredictProbPair> pps = null;
		FlowLineNode<?> fln = start;
		CSFlowLineQueue csdflq = null;
		if (start == null)
		{
			csdflq = new VirtualCSFlowLineQueue(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(
				-1, null, "", null, false, false, null, sh), 0));
		}
		else {
			csdflq = new CSFlowLineQueue(start);
		}
		int expectsize = PredictMetaInfo.OneExtendMaxSequence;
		if (level == 0)
		{
			expectsize = PredictMetaInfo.OneExtendFirstMaxSequence;
			fln = pretrylast;
			if (lastsilbppps != null)
			{
				pps = lastsilbppps;
			} else {
				pps = pi.InferNextGeneration(alc, expectsize, fln, null);
			}
		}
		else
		{
			if (lastsilbppps != null)
			{
				pps = lastsilbppps;
			} else {
				pps = pi.InferNextGeneration(alc, expectsize, fln, pretrylast);
			}
		}
		Iterator<PredictProbPair> pitr = pps.iterator();
		int keylen = 0;
		while (pitr.hasNext())
		{
			if (level == 0)
			{
				totalsuccess = 0;
				totalstep = 0;
			}
			if (TotalStopCondition())
			{
				break;
			}
			
			PredictProbPair ppp = pitr.next();
			if (keylen > ppp.getKeylen() && hassilb)
			{
				break;
			}
			keylen = ppp.getKeylen();
			Sentence pred = ppp.getPred();
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb(), aoi);
			statement predsmt = pred.getSmt();
			try {
				
				List<FlowLineNode<CSFlowLineData>> addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				totalstep++;
				
				if (addnodes != null && addnodes.size() > 0) {
					List<PredictProbPair> ppps = null;
					Iterator<FlowLineNode<CSFlowLineData>> aitr = addnodes.iterator();
					while (aitr.hasNext()) {
						if (TotalStopCondition())
						{
							break;
						}
						
						FlowLineNode<CSFlowLineData> addnode = aitr.next();
						boolean over = false;
						try {
							FlowLineNode<CSFlowLineData> lastone = null;
							if (!(ClassInstanceOfUtil.ObjectInstanceOf(csdflq, VirtualCSFlowLineQueue.class))) {
								lastone = (FlowLineNode<CSFlowLineData>) fln;
							}
							CSFlowLineData addnodedata = addnode.getData();
							
							if (CodeCompletionMetaInfo.DebugMode)
							{
								PrintWholeTrace(lastone, addnodedata.getSete().getSentence());
							}
							
							Stack<Integer> signals = new Stack<Integer>();
							try {
								addnodedata.HandleStackSignal(signals);
							} catch (CodeSynthesisException e) {
							}
							over = predsmt.HandleOverSignal(new FlowLineStack(lastone, signals));
							addnode.setCouldextend(!over);
						} catch (CodeSynthesisException e) {
							
							System.err.println(
									"Error occurs when doing code synthesis, this predict and the following will be ignored.");
							e.printStackTrace();
							continue;
						}
						if (over) {
							csfl.AddCodeSynthesisOver(addnode, pred);
							totalsuccess++;
						} else {
							if (ClassInstanceOfUtil.ObjectInstanceOf(csdflq, VirtualCSFlowLineQueue.class)) {
								// means first infer level.
								csfl.AddToFirstLevel(addnode, (FlowLineNode<Sentence>) fln);
							} else {
								csfl.AddToNextLevel(addnode, (FlowLineNode<CSFlowLineData>) fln);
							}
							ppps = RecursiveCodePredictAndSynthesis(level+1, addnode, aitr.hasNext(), ppps);
						}
					}
				}
			} catch (CodeSynthesisException e) {
				// testing
				System.err.println(
						"Error occurs when doing code synthesis, this predict and the following will be ignored.");
				e.printStackTrace();
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			} catch (Error e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return pps;
	}
	
	private String PrintWholeTrace(FlowLineNode<?> lastone, String mlast) {
		FlowLineNode<?> tmp = lastone;
		String one = mlast;
		while (tmp != null)
		{
			Object obj = tmp.getData();
			if (obj instanceof CSFlowLineData) {
				one = ((CSFlowLineData)obj).getSete().getSentence() + " " + one;
			} else {
				if (obj instanceof Sentence) {
					one = ((Sentence)obj).getSentence() + " " + one;
				} else {
					System.err.println("What the fuck? the element of FlowLineNode is not Sentence or CSFlowLineData? Serious error, the system will exit.");
					System.exit(1);
				}
			}
			tmp = tmp.getPrev();
		}
		String trace = "one trace:" + one;
		System.err.println(trace);
		return trace;
	}

	public boolean TotalStopCondition()
	{
		if (totalsuccess >= PredictMetaInfo.OneFirstMaxTotalSuccess || totalstep >= PredictMetaInfo.OneExtendFirstTotalStep)
		{
			return true;
		}
		return false;
	}

}