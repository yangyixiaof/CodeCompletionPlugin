package cn.yyx.contentassist.codeutils;

public abstract class nameStatement extends expressionStatement{
	
	/*identifier id = null;
	
	public nameStatement(identifier name) {
		this.id = name;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof nameStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof nameStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		id.HandleCodeSynthesis(squeue, expected, handler, fcs, ai);
		squeue.add(fcs);
		return false;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return id.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}*/

}
