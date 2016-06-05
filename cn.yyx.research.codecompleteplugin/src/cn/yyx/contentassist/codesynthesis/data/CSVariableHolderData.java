package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSVariableHolderData extends CSFlowLineData {

	private String varname = null;
	private boolean needensuretype = false;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public CSVariableHolderData(String varname, boolean needensuretype, CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.getTck(),
				cd.getHandler());
		if (cd.isHashole())
		{
			this.setHashole(true);
		}
		this.setVarname(varname);
		this.setNeedensuretype(needensuretype);
		this.setCsep(cd.getCsep());
		this.setScm(cd.getSynthesisCodeManager());
		this.setExtraData(cd.getExtraData());
	}

	public CSVariableHolderData(String varname, boolean needensuretype, Integer id, Sentence sete, String data, CCType dcls, 
			TypeComputationKind tck, SynthesisHandler handler) {
		super(id, sete, data, dcls, tck, handler);
		this.setVarname(varname);
		this.setNeedensuretype(needensuretype);
	}
	
	@Override
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix, CSFlowLineQueue squeue,
			CSStatementHandler smthandler, TypeComputationKind tck) throws CodeSynthesisException {
		if (needensuretype && d2.getDcls() != null)
		{
			AddToEveryRexpParNodeExtraVariableHolderInfo(d2.getDcls());
		}
		else
		{
			if (getDcls() != null)
			{
				AddToEveryRexpParNodeExtraVariableHolderInfo(getDcls());
			}
		}
		return super.Merge(prefix, concator, d2, postfix, squeue, smthandler, tck);
	}
	
	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public boolean isNeedensuretype() {
		return needensuretype;
	}

	public void setNeedensuretype(boolean needensuretype) {
		this.needensuretype = needensuretype;
	}
	
	private void AddToEveryRexpParNodeExtraVariableHolderInfo(CCType dcls)
	{
		getExtraData().AddExtraData(CSDataMetaInfo.VariableHolders, new CSVariableHolderExtraInfo(varname, dcls));
	}

}