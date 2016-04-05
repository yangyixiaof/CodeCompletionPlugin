package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class selfClassMemberInvoke extends classInvoke{
	
	referedExpression rexp = null;
	
	public selfClassMemberInvoke(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		String mcode = realhandler.getMethodname();
		if (rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> ls = rexp.HandleCodeSynthesis(squeue, realhandler);
			String rexpcode = ls.get(0).getData().getData();
			mcode = rexpcode + "." + mcode;
		}
		return CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, smthandler, mcode);
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		String mcode = ai.getMethodName();
		CSNode recs = new CSNode(CSNodeType.HalfFullExpression);
		if (rexp != null)
		{
			boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, recs, ai);
			if (conflict)
			{
				return true;
			}
		}
		CSNode spec = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, expected, handler, spec, ai, mcode);
		if (conflict)
		{
			return true;
		}
		result.SetCSNodeContent(CSNodeHelper.ConcatTwoNodes(recs, spec, ".", -1));
		// result.AddOneData(mcode, null);
		return false;
	}*/

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof selfClassMemberInvoke)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof selfClassMemberInvoke)
		{
			return 1;
		}
		return 0;
	}
	
}