package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

public class arrayAccessStatement extends statement{
	
	referedExpression rarr = null;
	referedExpression rexp = null;
	boolean accessEnd = false;
	
	public arrayAccessStatement(referedExpression rarr, referedExpression rexp, boolean accessEnd) {
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
		String postfix = null;
		if (accessEnd)
		{
			postfix = "]";
		}
		CSFlowLineHelper.ConcateTwoFlowLineNodeList(null, rals, "[", rels, postfix, TypeComputationKind.NoOptr, squeue, smthandler, (accessEnd ? null : StructureSignalMetaInfo.ArrayAccessBlcok));
		return null;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		if (!accessEnd)
		{
			cstack.SetLastStructureSignal(StructureSignalMetaInfo.ArrayAccessBlcok);
		}
		return false;
	}
	
}