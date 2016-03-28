package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class ifStatement extends statement{
	
	referedExpression rexp = null;
	
	public ifStatement(referedExpression rexp) {
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

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		int waitkind = cstack.peek();
		if (waitkind == PredictMetaInfo.AllKindWaitingOver)
		{
			cstack.pop();
		}
		cstack.push(PredictMetaInfo.IfOver);
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		// TODO Auto-generated method stub
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
	}
	
}