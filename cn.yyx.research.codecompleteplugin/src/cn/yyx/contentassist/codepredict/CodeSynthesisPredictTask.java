package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
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
import cn.yyx.contentassist.codesynthesis.typeutil.SameTypeConflictException;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.Assignment;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
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

	public CodeSynthesisPredictTask(PreTryFlowLineNode<Sentence> pretrylastpara, SynthesisHandler sh, AeroLifeCycle alc,
			CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi, int id) {
		this.pretrylast = pretrylastpara;
		this.pretrylastwholetrace = PrintWholeTrace(pretrylastpara, "", "whole");
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
			// return null;
			return null;
		}
		if (TotalStopCondition()) {
			// return null;
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
				PrintWholeTrace(start, pred.getSentence(), "pre-handle");
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

					// List<PredictProbPair> ppps = null;
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
								PrintWholeTrace(lastone, addnodedata.getSete().getSentence(), "handled");
							}

							Stack<Integer> signals = new Stack<Integer>();
							try {
								addnodedata.HandleStackSignal(signals);
							} catch (CodeSynthesisException e) {
							}
							over = predsmt.HandleOverSignal(new FlowLineStack(lastone, signals));
							addnode.setCouldextend(!over);
							if (over) {
								if (addnode.getSynthesisdata() != null) {
									csfl.AddCodeSynthesisOver(new FlowLineNode<CSFlowLineData>(addnode.getSynthesisdata(), addnode.getProbability()), pred);
								} else {
									csfl.AddCodeSynthesisOver(addnode, pred);
								}
								totalsuccess++;
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

	private String PrintWholeTrace(FlowLineNode<?> lastone, String mlast, String additioninfo) {
		FlowLineNode<?> tmp = lastone;
		String one = mlast;
		while (tmp != null) {
			Object obj = tmp.getData();
			if (obj instanceof CSFlowLineData) {
				one = ((CSFlowLineData) obj).getSete().getSentence() + " " + one;
			} else {
				if (obj instanceof Sentence) {
					one = ((Sentence) obj).getSentence() + " " + one;
				} else {
					System.err.println(
							"What the fuck? the element of FlowLineNode is not Sentence or CSFlowLineData? Serious error, the system will exit.");
					System.exit(1);
				}
			}
			tmp = tmp.getPrev();
		}
		String trace = additioninfo + " one trace:" + one;
		System.err.println(trace);
		return trace;
	}

	public boolean TotalStopCondition() {
		if (totalsuccess >= PredictMetaInfo.OneFirstMaxTotalSuccess
				|| totalstep > PredictMetaInfo.OneExtendFirstTotalStep) {
			return true;
		}
		return false;
	}

}