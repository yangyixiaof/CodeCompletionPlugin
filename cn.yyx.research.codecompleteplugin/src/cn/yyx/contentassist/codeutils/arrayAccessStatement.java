package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArrayAccessStartProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.CSFlowLineTypeCheckRefiner;

public class arrayAccessStatement extends expressionStatement {
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	boolean accessEnd = false;
	
	public arrayAccessStatement(String smtcode, referedExpression rarr, referedExpression rexp, boolean accessEnd) {
		super(smtcode);
		this.rarr = rarr;
		this.rexp = rexp;
		this.accessEnd = accessEnd;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof arrayAccessStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof arrayAccessStatement)
		{
			return 0.4 + 0.6*(rarr.Similarity(((arrayAccessStatement) t).rarr) + rexp.Similarity(((arrayAccessStatement) t).rexp));
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode smt = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = false;
		CSNode csarr = new CSNode(CSNodeType.TempUsed);
		conflict = rarr.HandleCodeSynthesis(squeue, expected, handler, csarr, null);
		if (conflict)
		{
			return true;
		}
		
		TypeCheck iit = new TypeCheck();
		iit.setExpreturntype("java.lang.Integer");
		iit.setExpreturntypeclass(Integer.class);
		
		expected.push(iit);
		CSNode cidx = new CSNode(CSNodeType.TempUsed);
		cidx.setPrefix("[");
		conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cidx, null);
		if (conflict)
		{
			return true;
		}
		if (accessEnd)
		{
			cidx.setPostfix("]");
		}
		
		smt.setDatas(CSNodeHelper.ConcatTwoNodesDatas(csarr, cidx, null, -1));
		squeue.add(smt);
		expected.pop();
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rals = rarr.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		rals = CSFlowLineTypeCheckRefiner.RetainTheArrayFlowLineNodes(rals);
		if (accessEnd)
		{
			rels = CSFlowLineTypeCheckRefiner.RetainTheFallThroughFlowLineNodes(rels, new CCType(int.class, "int"));
		}
		/*if (rals == null || rals.size() == 0 || rels == null || rels.size() == 0)
		{
			return null;
		}*/
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> fmls = CSFlowLineHelper.ForwardConcate(null, rals, "[", rels, null, squeue, smthandler, null);
		if (fmls == null || fmls.size() == 0)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> itr = fmls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			if (accessEnd) {
				// do nothing.
			} else {
				// result.add(new FlowLineNode<CSFlowLineData>(new CSArrayAccessStartData(fln.getData()), fln.getProbability()));
				fln.getData().setCsep(new CSArrayAccessStartProperty(null));
			}
			result.add(fln);
		}
		// (accessEnd ? null : StructureSignalMetaInfo.ArrayAccessBlcok
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		/*if (!accessEnd)
		{
			cstack.SetLastStructureSignal(DataStructureSignalMetaInfo.ArrayAccessBlcok);
		}*/
		return false;
	}
	
}