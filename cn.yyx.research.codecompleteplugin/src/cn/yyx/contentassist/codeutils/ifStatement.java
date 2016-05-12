package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.CSFlowLineTypeCheckHelper;

public class ifStatement extends statement{
	
	referedExpression rexp = null;
	
	public ifStatement(String smtcode, referedExpression rexp) {
		super(smtcode);
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof ifStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof ifStatement)
		{
			return 0.4 + 0.6*(rexp.Similarity(((ifStatement) t).rexp));
		}
		return 0;
	}

	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		int waitkind = cstack.peek();
		if (waitkind == StructureSignalMetaInfo.AllKindWaitingOver)
		{
			cstack.pop();
		}
		cstack.push(StructureSignalMetaInfo.IfOver);
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		TypeCheck tc = new TypeCheck();
		tc.setExpreturntype("Boolean");
		tc.setExpreturntypeclass(Boolean.class);
		expected.add(tc);
		
		CSNode recs = new CSNode(CSNodeType.WholeStatement);
		rexp.HandleCodeSynthesis(squeue, expected, handler, recs, ai);
		recs.setPrefix("if (");
		recs.setPostfix(") {\n}");
		
		expected.pop();
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineTypeCheckHelper.RetainTheFallThroughFlowLineNodes(CSFlowLineHelper.ConcateOneFlowLineList("if (", rels, ") {\n}"), new CCType(Boolean.class, "Boolean"));
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}