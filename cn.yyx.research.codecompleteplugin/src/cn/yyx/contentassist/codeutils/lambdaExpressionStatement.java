package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArgTypeListData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSDirectLambdaHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class lambdaExpressionStatement extends statement {

	argTypeList typelist = null; // warning: typelist could be null.
	referedExpression rexp = null; // warning: rexp could be null.

	public lambdaExpressionStatement(String smtcode, argTypeList tlist, referedExpression rexp) {
		super(smtcode);
		this.typelist = tlist;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof lambdaExpressionStatement) {
			if (Similarity(t) > PredictMetaInfo.SequenceSimilarThreshold) {
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lambdaExpressionStatement) {
			int count = 0;
			if (typelist != null) {
				count = typelist.Size();
			}
			int tcount = 0;
			if (((lambdaExpressionStatement) t).typelist != null) {
				tcount = ((lambdaExpressionStatement) t).typelist.Size();
			}
			int div = Math.max(count + 1, tcount + 1);
			int dvi = Math.min(count + 1, tcount + 1);
			return (dvi * 1.0) / (div * 1.0);
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		if (typelist == null) {
			if (rexp == null) {
				result.add(new FlowLineNode<CSFlowLineData>(new CSLambdaData(true, squeue.GenerateNewNodeId(),
						smthandler.getSete(), "()->{}", null, null, squeue.GetLastHandler()), smthandler.getProb()));
			} else {
				List<FlowLineNode<CSFlowLineData>> fres = null;
				List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
				fres = CSFlowLineHelper.ConcateOneFlowLineList("()->", rels, null);
				if (fres == null || fres.size() == 0) {
					return null;
				}
				Iterator<FlowLineNode<CSFlowLineData>> itr = fres.iterator();
				while (itr.hasNext()) {
					FlowLineNode<CSFlowLineData> fln = itr.next();
					result.add(new FlowLineNode<CSFlowLineData>(new CSLambdaData(false, null, fln.getData()),
							fln.getProbability()));
				}
			}
			return result;
		} else {
			if (rexp == null) {
				List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
				if ((tpls == null || tpls.size() == 0)) {
					return null;
				}
				tpls = CSFlowLineHelper.ConcateOneFlowLineList("(", tpls, ")->{}");
				Iterator<FlowLineNode<CSFlowLineData>> tpitr = tpls.iterator();
				while (tpitr.hasNext()) {
					FlowLineNode<CSFlowLineData> tpfln = tpitr.next();
					List<String> tadnames = ((CSArgTypeListData) tpfln.getData()).getTpandnames();
					result.add(new FlowLineNode<CSFlowLineData>(new CSLambdaData(true, tadnames, tpfln.getData()),
							tpfln.getProbability()));
				}
			} else {
				List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
				if ((tpls == null || tpls.size() == 0)) {
					return null;
				}
				Iterator<FlowLineNode<CSFlowLineData>> tpitr = tpls.iterator();
				while (tpitr.hasNext()) {
					FlowLineNode<CSFlowLineData> tpfln = tpitr.next();
					List<FlowLineNode<CSFlowLineData>> tres = new LinkedList<FlowLineNode<CSFlowLineData>>();
					tres.add(tpfln);
					List<String> tadnames = ((CSArgTypeListData) tpfln.getData()).getTpandnames();
					
					List<FlowLineNode<CSFlowLineData>> rels = null;
					CSDirectLambdaHandler cdlh = new CSDirectLambdaHandler(tadnames, smthandler);
					if (rexp instanceof commonVarRef) {
						rels = rexp.HandleCodeSynthesis(squeue, cdlh);
					} else {
						rels = rexp.HandleCodeSynthesis(squeue, smthandler);
					}
					if ((rels == null || rels.size() == 0))
					{
						continue;
					}
					
					List<FlowLineNode<CSFlowLineData>> tts = CSFlowLineHelper.ForwardConcate("(", tres, ")->", rels,
							null, squeue, smthandler, null);
					Iterator<FlowLineNode<CSFlowLineData>> itr = tts.iterator();
					while (itr.hasNext()) {
						FlowLineNode<CSFlowLineData> fln = itr.next();
						result.add(new FlowLineNode<CSFlowLineData>(new CSLambdaData(false, tadnames, fln.getData()),
								fln.getProbability()));
					}
				}
			}
			return result;
		}
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		// cstack.EnsureAllSignalNull();
		return false;
	}

}