package cn.yyx.contentassist.codeutils;

public abstract class fieldAccess extends referedExpression{
	
	// identifier id = null;
	// referedExpression rexp = null;
	
	/*public fieldAccess(identifier name, referedExpression rexp) {
		this.id = name;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return 0.4 + 0.6*(0.6*id.Similarity(((fieldAccess) t).id) + 0.4*(rexp.Similarity(((fieldAccess) t).rexp)));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode idcs = new CSNode(CSNodeType.TempUsed);
		id.HandleCodeSynthesis(squeue, expected, handler, idcs, null);
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(idcs.GetFirstDataWithoutTypeCheck());
		nai.setDirectlyMemberIsMethod(false);
		result.setContenttype(CSNodeType.HalfFullExpression);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, result, nai);
		return conflict;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		CSFieldAccessStatementHandler csfash = new CSFieldAccessStatementHandler(idls.get(0).getData().getData(), smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csfash);
		if (!(csfash.isFieldused()))
		{
			List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(rels, idls, squeue, smthandler, ".");
			if (ls.size() == 0)
			{
				return CSFlowLineHelper.ForwardMerge(null, idls, ".", rels, null, squeue, smthandler, null, null);
			}
			return ls;
		}
		return rels;
	}*/
	
}