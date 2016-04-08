package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.NameConvention;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class variableDeclarationHolderStatement extends statement{
	
	referedExpression rexp = null;
	
	public variableDeclarationHolderStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof variableDeclarationHolderStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		double prob = 0;
		if (t instanceof variableDeclarationHolderStatement)
		{
			if (rexp == null && ((variableDeclarationHolderStatement)t).rexp == null)
			{
				prob = 1;
			}
			else
			{
				if ((rexp != null && ((variableDeclarationHolderStatement)t).rexp == null) || (rexp == null && ((variableDeclarationHolderStatement)t).rexp != null))
				{
					prob = 0.5;
				}
				else
				{
					prob = rexp.Similarity(((variableDeclarationHolderStatement) t).rexp);
				}
			}
			return 0.4 + 0.6*(prob);
		}
		return 0;
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		String type = handler.getRecenttype();
		String name = NameConvention.GetAbbreviationOfType(type);
		ScopeOffsetRefHandler schandler = handler.getScopeOffsetRefHandler();
		if (rexp != null)
		{
			String modifidedname = schandler.GenerateNewDeclaredVariable(name, type);
			CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
			boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			Map<String, TypeCheck> po = cs.getDatas();
			if (po.size() >= 1)
			{
				TypeCheck tc = cs.GetFirstTypeCheck();
				if (tc != null && tc.getExpreturntypeclass() != null)
				{
					schandler.DeleteRecentlyAddedType(type);
					modifidedname = schandler.GenerateNewDeclaredVariable(NameConvention.GetAbbreviationOfType(tc.getExpreturntype()), tc.getExpreturntype());
				}
			}
			cs.setPrefix(modifidedname+"=");
		}
		else
		{
			String modifidedname = schandler.GenerateNewDeclaredVariable(name, type);
			CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
			cs.AddOneData(modifidedname, null);
			squeue.add(cs);
		}
		return false;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}