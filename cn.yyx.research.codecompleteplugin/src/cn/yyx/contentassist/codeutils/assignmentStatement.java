package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;

public class assignmentStatement extends expressionStatement{
	referedExpression left = null;
	String optr = null;
	referedExpression right = null;
	
	public assignmentStatement(String smtcode, referedExpression left, String optr, referedExpression right) {
		super(smtcode);
		this.left = left;
		this.optr = optr;
		this.right = right;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof assignmentStatement)
		{
			if (optr.equals(((assignmentStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof assignmentStatement)
		{
			return 0.3*(left.Similarity(((assignmentStatement) t).left)) + 0.4*(optr.equals(((assignmentStatement) t).optr) ? 1 : 0) + 0.3*(right.Similarity(((assignmentStatement) t).right));
		}
		return 0;
	}

	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode lt = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = left.HandleCodeSynthesis(squeue, expected, handler, lt, null);
		if (conflict)
		{
			return true;
		}
		lt.setPostfix(optr);
		CSNode rt = new CSNode(CSNodeType.ReferedExpression);
		conflict = right.HandleCodeSynthesis(squeue, expected, handler, rt, null);
		if (conflict)
		{
			return true;
		}
		CSNode fin = new CSNode(CSNodeType.WholeStatement);
		fin.setDatas(CSNodeHelper.ConcatTwoNodesDatasWithTypeChecking(lt, rt, -1));
		squeue.add(fin);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> leftls = left.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> rightls = right.HandleCodeSynthesis(squeue, smthandler);
		TypeComputationKind tc = TypeComputer.ComputeKindFromRawString(optr);
		return CSFlowLineHelper.ForwardConcate(null, leftls, optr, rightls, null, squeue, smthandler, tc, tc);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}