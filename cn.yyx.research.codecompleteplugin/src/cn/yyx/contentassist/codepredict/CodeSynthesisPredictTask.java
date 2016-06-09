package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictInfer;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.VirtualCSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.SameTypeConflictException;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.Assignment;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.LevelArrayHelper;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.EmergencyBack;
import cn.yyx.contentassist.commonutils.GenBlock;
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
	int maxlevel = -1;
	int[] levelconsumed = null;

	public CodeSynthesisPredictTask(PreTryFlowLineNode<Sentence> pretrylastpara, SynthesisHandler sh, AeroLifeCycle alc,
			CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi, int id) {
		this.pretrylast = pretrylastpara;
		this.pretrylastwholetrace = FlowLineHelper.PrintWholeTrace(pretrylastpara, "", "whole");
		this.sh = sh;
		this.alc = alc;
		this.csfl = csfl;
		this.aoi = aoi;
		this.pi = new PredictInfer(id);
	}

	@Override
	public void run() {
		RecursiveCodePredictAndSynthesis(0, null, false, new LinkedList<GenBlock>());
	}

	@SuppressWarnings("unchecked")
	private EmergencyBack RecursiveCodePredictAndSynthesis(int level, FlowLineNode<CSFlowLineData> start,
			boolean hassilb, List<GenBlock> gennodes) {
		if (level >= PredictMetaInfo.MaxExtendLength) {
			return null;
		}
		if (TotalStopCondition()) {
			return null;
		}
		List<PredictProbPair> pps = null;
		TypeComputationKind starttck = null;
		if (start != null) {
			starttck = start.getData().getTck();
		}
		FlowLineNode<?> fln = start;
		CSFlowLineQueue csdflq = null;
		if (start == null) {
			csdflq = new VirtualCSFlowLineQueue(
					new FlowLineNode<CSFlowLineData>(new CSFlowLineData(-1, null, "", null, null, sh), 0));
		} else {
			csdflq = new CSFlowLineQueue(start);
		}
		int expectsize = PredictMetaInfo.OneExtendMaxSequence;
		if (level == 0) {
			expectsize = PredictMetaInfo.OneExtendFirstMaxSequence;
			fln = pretrylast;
			pi.BeginOperation();
			pps = pi.InferNextGeneration(alc, expectsize, fln, null);
			pi.EndOperation();
		} else {
			pi.BeginOperation();
			pps = pi.InferNextGeneration(alc, expectsize, fln, pretrylast);
			pi.EndOperation();
		}
		Iterator<PredictProbPair> pitr = pps.iterator();
		int keylen = 0;
		while (pitr.hasNext()) {
			// clear tck.
			if (start != null) {
				if (starttck != null) {
					try {
						start.getData().setTck((TypeComputationKind) starttck.clone());
					} catch (CloneNotSupportedException e) {
						System.err.println("Clone error? What the fuck!");
						e.printStackTrace();
						System.exit(1);
					}
				} else {
					start.getData().setTck(null);
				}
			}

			if (level == 0) {
				totalsuccess = 0;
				totalstep = 0;
				maxlevel = -1;
				levelconsumed = null;
			}
			if (TotalStopCondition()) {
				break;
			}

			PredictProbPair ppp = pitr.next();
			if (keylen > ppp.getKeylen() && hassilb) {
				break;
			}
			keylen = ppp.getKeylen();
			Sentence pred = ppp.getPred();
			
			if (CodeCompletionMetaInfo.DebugMode) {
				String one = FlowLineHelper.PrintWholeTrace(start, pred.getSentence(), "pre-handle");
				if (one.equals("MI@toString(@C0?0) DH@);Pr"))
				{
					System.err.println("Strange caught.");
				}
			}
			
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb(), aoi);
			statement predsmt = pred.getSmt();
			try {
				
				// debug code, not remove.
				if (predsmt.toString().contains("println"))
				{
					System.err.println("println catched.");
				}
				
				List<FlowLineNode<CSFlowLineData>> addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				totalstep++;

				if (addnodes != null && addnodes.size() > 0) {

					if (gennodes.size() > level) {
						gennodes.set(level, new GenBlock(addnodes));
					} else {
						gennodes.add(new GenBlock(addnodes));
					}
					
					int i = 0;
					int len = addnodes.size();
					for (i = 0; i < len; i++) {
						if (TotalStopCondition()) {
							break;
						}
						gennodes.get(level).setIdx(i);
						FlowLineNode<CSFlowLineData> addnode = addnodes.get(i);// aitr.next();
						boolean over = false;
						try {
							FlowLineNode<CSFlowLineData> lastone = null;
							if (!(ClassInstanceOfUtil.ObjectInstanceOf(csdflq, VirtualCSFlowLineQueue.class))) {
								lastone = (FlowLineNode<CSFlowLineData>) fln;
							}
							CSFlowLineData addnodedata = addnode.getData();

							if (CodeCompletionMetaInfo.DebugMode) {
								FlowLineHelper.PrintWholeTrace(lastone, addnodedata.getSete().getSentence(), "handled");
							}

							Stack<Integer> signals = new Stack<Integer>();
							try {
								addnodedata.HandleStackSignal(signals);
							} catch (CodeSynthesisException e) {
							}
							over = predsmt.HandleOverSignal(new FlowLineStack(lastone, signals));
							addnode.setCouldextend(!over);
							if (over) {
								if (maxlevel == -1) {
									maxlevel = level;
									levelconsumed = new int[maxlevel+2];
									LevelArrayHelper.InitialIntArrayToZero(levelconsumed);
								}
								if (addnode.getSynthesisdata() != null) {
									csfl.AddCodeSynthesisOver(new FlowLineNode<CSFlowLineData>(addnode.getSynthesisdata(), addnode.getProbability()), pred);
								} else {
									csfl.AddCodeSynthesisOver(addnode, pred);
								}
								totalsuccess++;
								boolean shouldreturn = LevelArrayHelper.CheckAndAddOneAccordToLevel(levelconsumed, level);
								if (shouldreturn)
								{
									return null;
								}
							} else {
								if (ClassInstanceOfUtil.ObjectInstanceOf(csdflq, VirtualCSFlowLineQueue.class)) {
									// means first infer level.
									csfl.AddToFirstLevel(addnode, (FlowLineNode<Sentence>) fln);
								} else {
									csfl.AddToNextLevel(addnode, (FlowLineNode<CSFlowLineData>) fln);
								}
								// Solved. aitr.hasNext() changes to hassilb ||
								// aitr.hasNext().
								EmergencyBack eb = RecursiveCodePredictAndSynthesis(level + 1, addnode,
										hassilb || (i < len - 1), gennodes);
								if (eb != null && eb.getNeedlevel() < level) {
									return eb;
								}
							}
						} catch (CodeSynthesisException e) {
							if (e instanceof SameTypeConflictException)
							{
								System.err.println("AssignTypeConflictException occurs.");
								throw e;
							}
							System.err.println(
									"Error occurs when doing code synthesis, this predict and the following will be ignored.");
							e.printStackTrace();
							continue;
						}
					}
				}
			} catch (SameTypeConflictException e) {
				int neededlevel = e.getPrelength() - 1;
				List<FlowLineNode<CSFlowLineData>> ads = gennodes.get(neededlevel).getGennodes();
				int cidx = gennodes.get(neededlevel).getIdx();
				int st = cidx + 1;
				boolean oped = false;
				for (int j = st; j < ads.size(); j++) {
					FlowLineNode<CSFlowLineData> adfln = ads.get(j);
					TypeComputationKind tck = adfln.getData().getTck();
					if (tck instanceof Assignment) {
						if (tck.getPre() == null) {
							continue;
						}
						if (TypeComputer.CCTypeSame(tck.getPre(), e.getNeedclass())) {
							FlowLineNode<CSFlowLineData> stfln = ads.get(st);
							ads.set(st, adfln);
							ads.set(j, stfln);
							oped = true;
							break;
						}
					}
				}
				// e.printStackTrace();
				if (oped) {
					return new EmergencyBack(neededlevel);
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
		return null;
		// return pps;
	}

	public boolean TotalStopCondition() {
		if (totalsuccess >= PredictMetaInfo.OneFirstMaxTotalSuccess
				|| totalstep > PredictMetaInfo.OneExtendFirstTotalStep) {
			return true;
		}
		return false;
	}

}