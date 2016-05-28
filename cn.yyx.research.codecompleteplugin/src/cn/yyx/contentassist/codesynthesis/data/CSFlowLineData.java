package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineData implements CSDataStructure{
	
	private String id = null;
	private Sentence sete = null;
	private String data = null;
	private CCType dcls = null;
	private SynthesisHandler handler = null;
	
	private boolean haspre = false;
	private boolean hashole = false;
	private TypeComputationKind pretck = TypeComputationKind.NoOptr;
	private TypeComputationKind posttck = TypeComputationKind.NoOptr;
	
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
	
	public CSFlowLineData(Integer id, Sentence sete, String data, CCType dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHaspre(haspre);
		this.setHashole(hashole);
		pretck = (pretck == null ? TypeComputationKind.NoOptr : pretck);
		posttck = (posttck == null ? TypeComputationKind.NoOptr : posttck);
		this.setPretck(pretck);
		this.setPosttck(posttck);
		this.setHandler(handler);
	}
	
	public CSFlowLineData(String id, Sentence sete, String data, CCType dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHaspre(haspre);
		this.setHashole(hashole);
		pretck = (pretck == null ? TypeComputationKind.NoOptr : pretck);
		posttck = (posttck == null ? TypeComputationKind.NoOptr : posttck);
		this.setPretck(pretck);
		this.setPosttck(posttck);
		this.setHandler(handler);
	}
	
	public CSFlowLineData(String id, Sentence sete, String data, CCType dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler, CSExtraProperty cseppara) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHaspre(haspre);
		this.setHashole(hashole);
		pretck = (pretck == null ? TypeComputationKind.NoOptr : pretck);
		posttck = (posttck == null ? TypeComputationKind.NoOptr : posttck);
		this.setPretck(pretck);
		this.setPosttck(posttck);
		this.setHandler(handler);
		this.setCsep(cseppara);
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

	/*public Integer getStructsignal() {
		return structsignal;
	}

	public void setStructsignal(Integer structsignal) {
		this.structsignal = structsignal;
	}*/

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

	public TypeComputationKind getPretck() {
		return pretck;
	}

	public void setPretck(TypeComputationKind pretck) {
		this.pretck = pretck;
	}

	public TypeComputationKind getPosttck() {
		return posttck;
	}

	public void setPosttck(TypeComputationKind posttck) {
		this.posttck = posttck;
	}

	public boolean isHaspre() {
		return haspre;
	}

	public void setHaspre(boolean haspre) {
		this.haspre = haspre;
	}

	public CSExtraData getExtraData() {
		return csed;
	}

	public void setExtraData(CSExtraData csed) {
		this.csed = csed;
	}

	/*public boolean isShouldskip() {
		return shouldskip;
	}

	public void setShouldskip(boolean shouldskip) {
		this.shouldskip = shouldskip;
	}*/
	
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
		return "id:" + id + ";sete:" + sete + ";data:" + data + (dcls == null ? ";dcls null" : (";dcls str:" + dcls.getClstr() + ";dcls rt:" + dcls.getCls()));
	}

	public CSExtraProperty getCsep() {
		return csep;
	}

	public void setCsep(CSExtraProperty csep) {
		this.csep = csep;
	}
	
}