package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSVariableHolderData extends CSFlowLineData {

	private String varname = null;
	private boolean needensuretype = false;
	
	public CSVariableHolderData(String varname, boolean needensuretype, CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.isHaspre(), cd.isHashole(), cd.getPretck(),
				cd.getPosttck(), cd.getHandler());
		this.setVarname(varname);
		this.setNeedensuretype(needensuretype);
	}

	public CSVariableHolderData(String varname, boolean needensuretype, Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
		this.setVarname(varname);
		this.setNeedensuretype(needensuretype);
	}
	
	@Override
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix, CSFlowLineQueue squeue,
			CSStatementHandler smthandler, TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		if (oneafter == null || oneafter == TypeComputationKind.NotSureOptr || oneafter == TypeComputationKind.NoOptr)
		{
			oneafter = getPosttck();
		}
		if (oneafter == null) {
			oneafter = TypeComputationKind.NoOptr;
		}
		if (beforetwo == null || beforetwo == TypeComputationKind.NotSureOptr || beforetwo == TypeComputationKind.NoOptr) {
			beforetwo = d2.getPretck();
		}
		if (beforetwo == null)
		{
			beforetwo = TypeComputationKind.NoOptr;
		}
		TypeComputationKind tck = TypeComputer.ChooseOne(oneafter, beforetwo);
		CCType clz = null; // Class<?> 
		clz = TypeComputer.ComputeType(getDcls(), d2.getDcls(), tck);
		if (needensuretype && d2.getDcls() != null)
		{
			AddToEveryRexpParNodeExtraVariableHolderInfo(d2.getDcls());
		}
		String str1 = getData();
		String str2 = d2.getData();
		String cnctcnt = (prefix == null ? "" : prefix) + str1 + (concator == null ? "" : concator) + str2
				+ (postfix == null ? "" : postfix);
		CSFlowLineData cf = new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), cnctcnt, clz,
				isHaspre(), d2.isHashole(), d2.getPretck(), d2.getPosttck(), getHandler());
		// merge extra data info.
		cf.setExtraData((CSExtraData) csed.SelfClosedMerge(d2.csed));
		return cf;
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