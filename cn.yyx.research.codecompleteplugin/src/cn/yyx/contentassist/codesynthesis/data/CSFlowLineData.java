package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineData implements CSDataStructure {
	
	private String id = null;
	private Sentence sete = null;
	private String data = null;
	private CCType dcls = null;
	private SynthesisHandler handler = null;
	
	// private boolean haspre = false;
	private boolean hashole = false;
	private TypeComputationKind tck = null;
	
	private CSExtraProperty csep = null;
	private SynthesisCodeManager scm = new SynthesisCodeManager();
	
	protected CSExtraData csed = new CSExtraData();
	
	public boolean HasSpecialProperty(Class<?> cls)
	{
		if (getClass().equals(cls))
		{
			return true;
		} else {
			if (csep != null && csep.getClass().equals(cls)) {
				return true;
			}
		}
		return false;
	}
	
	// this boolean field is used to skip some useless node.
	// private boolean shouldskip = false;
	// this boolean field should be set at some specific kind of statement.
	private boolean isonestatementend = false;
	
	public CSFlowLineData(Integer id, Sentence sete, String data, CCType dcls, TypeComputationKind tck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		this.setDcls(dcls);
		// this.setHashole(hashole);
		this.setTck(tck);
		this.setHandler(handler);
	}
	
	// boolean haspre, boolean hashole, 
	public CSFlowLineData(String id, Sentence sete, String data, CCType dcls, TypeComputationKind tck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		// this.setHaspre(haspre);
		// this.setHashole(hashole);
		this.setTck(tck);
		this.setHandler(handler);
	}
	
	public CSFlowLineData(String id, Sentence sete, String data, CCType dcls, TypeComputationKind tck, SynthesisHandler handler, CSExtraProperty cseppara) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		// this.setHaspre(haspre);
		// this.setHashole(hashole);
		this.setTck(tck);
		this.setHandler(handler);
		this.setCsep(cseppara);
	}
	
	public CSFlowLineData(String id, CSFlowLineData dt) {
		this(id, dt.getSete(), dt.getData(), dt.getDcls(), dt.getTck(), dt.getHandler(), dt.getCsep());
		this.setExtraData(dt.getExtraData());
		// this.setCsep(dt.getCsep());
		// this.setScm(dt.getSynthesisCodeManager());
		// the above two should not exist on the data invoke this construction.
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public boolean isHashole() {
		return hashole;
	}

	public void setHashole(boolean hashole) {
		this.hashole = hashole;
	}

	public SynthesisHandler getHandler() {
		return handler;
	}

	public void setHandler(SynthesisHandler handler) {
		this.handler = handler;
	}

	public SynthesisCodeManager getSynthesisCodeManager() {
		return scm;
	}

	public void setScm(SynthesisCodeManager scm) {
		this.scm = scm;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sentence getSete() {
		return sete;
	}

	public void setSete(Sentence sete) {
		this.sete = sete;
	}

	public boolean isIsonestatementend() {
		return isonestatementend;
	}

	public void setIsonestatementend(boolean isonestatementend) {
		this.isonestatementend = isonestatementend;
	}
	
	public CSExtraData getExtraData() {
		return csed;
	}

	public void setExtraData(CSExtraData csed) {
		this.csed = csed;
	}
	
	private boolean TCKNotOver(TypeComputationKind tckpara) throws TypeConflictException
	{
		return tckpara != null && !tckpara.HandleOver();
	}
	
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix, CSFlowLineQueue squeue,
			CSStatementHandler smthandler, TypeComputationKind tck) throws CodeSynthesisException {
		if (tck != null) {
			if (TCKNotOver(this.getTck()) || TCKNotOver(d2.getTck()))
			{
				new Exception("TypeComputationKind conflict.").printStackTrace();
				throw new CodeSynthesisException("TypeComputationKind conflict.");
			}
		} else {
			if (TCKNotOver(this.getTck()) && !TCKNotOver(d2.getTck()))
			{
				// tck = d2.getTck();
				tck = getTck();
			}
			if (!TCKNotOver(this.getTck()) && TCKNotOver(d2.getTck()))
			{
				// tck = getTck();
				tck = d2.getTck();
			}
			if (TCKNotOver(this.getTck()) && TCKNotOver(d2.getTck()))
			{
				new Exception("two all have not over tck, what the fuck?").printStackTrace();
				throw new TypeConflictException("two all have not over tck, what the fuck?");
			}
		}
		CCType clz = null;
		if (tck != null)
		{
			tck.HandlePre(getDcls());
			tck.HandlePost(d2.getDcls());
			if (tck.HandleOver()) {
				clz = tck.HandleResult();
				tck = null;
			}
		}
		String str1 = getData();
		String str2 = d2.getData();
		String cnctcnt = (prefix == null ? "" : prefix) + str1 + (concator == null ? "" : concator) + str2
				+ (postfix == null ? "" : postfix);
		CSFlowLineData cf = new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), cnctcnt, clz,
				tck, getHandler());
		cf.setHashole(d2.isHashole());
		// merge extra data info.
		cf.setExtraData((CSExtraData) csed.SelfClosedMerge(d2.csed));
		return cf;
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		if (getCsep() != null)
		{
			getCsep().HandleStackSignal(signals);
		}
	}

	public CCType getDcls() {
		return dcls;
	}

	public void setDcls(CCType dcls) {
		this.dcls = dcls;
	}
	
	@Override
	public String toString() {
		return "id:" + id + ";sete:" + sete + ";data:" + data + (dcls == null ? ";dcls null" : (";dcltr:" + dcls.getClstr() + ";dcls rt:" + dcls.getCls()));
	}

	public CSExtraProperty getCsep() {
		return csep;
	}

	public void setCsep(CSExtraProperty csep) {
		this.csep = csep;
	}

	public TypeComputationKind getTck() {
		return tck;
	}

	public void setTck(TypeComputationKind tck) {
		this.tck = tck;
	}
	
}