package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class enumConstantDeclarationStatement extends statement{
	
	identifier id = null;
	argumentList arglist = null;
	
	public enumConstantDeclarationStatement(identifier name, argumentList arglist) {
		this.id = name;
		this.arglist = arglist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			if (id.CouldThoughtSame(((enumConstantDeclarationStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((enumConstantDeclarationStatement) t).id));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode idcs = new CSNode(CSNodeType.TempUsed);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, idcs, null);
		if (arglist == null)
		{
			idcs.setContenttype(CSNodeType.WholeStatement);
			squeue.add(idcs);
		}
		else
		{
			AdditionalInfo nai = new AdditionalInfo();
			nai.setMethodName(idcs.GetFirstDataWithoutTypeCheck());
			CSNode acs = new CSNode(CSNodeType.WholeStatement);
			conflict = arglist.HandleCodeSynthesis(squeue, expected, handler, acs, nai);
			squeue.add(acs);
		}
		return conflict;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> nls = id.HandleCodeSynthesis(squeue, smthandler);
		String methodname = nls.get(0).getData().getData();
		CSMethodStatementHandler csmsh = new CSMethodStatementHandler(smthandler);
		csmsh.setNextstart(squeue.getLast());
		return arglist.HandleMethodIntegrationCodeSynthesis(squeue, smthandler, methodname);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return true;
	}
	
}