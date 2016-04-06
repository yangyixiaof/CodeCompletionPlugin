package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineData {
	
	private String id = null;
	private Sentence sete = null;
	private String data = null;
	private Integer structsignal = null;
	private Class<?> dcls = null;
	private boolean hashole = false;
	private TypeComputationKind tck = TypeComputationKind.NoOptr;
	private SynthesisHandler handler = null;
	private SynthesisCodeManager scm = new SynthesisCodeManager();
	
	// this boolean field should be set at some specific kind of statement.
	private boolean isonestatementend = false;
	
	public CSFlowLineData(Integer id, Sentence sete, String data, Integer structsignal, Class<?> dcls, boolean hashole, TypeComputationKind tck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHashole(hashole);
		this.setTck(tck);
		this.setHandler(handler);
	}
	
	public CSFlowLineData(String id, Sentence sete, String data, Integer structsignal, Class<?> dcls, boolean hashole, TypeComputationKind tck, SynthesisHandler handler) {
		this.setId(id);
		this.setSete(sete);
		this.setData(data);
		this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHashole(hashole);
		this.setTck(tck);
		this.setHandler(handler);
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Class<?> getDcls() {
		return dcls;
	}

	public void setDcls(Class<?> dcls) {
		this.dcls = dcls;
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

	public Integer getStructsignal() {
		return structsignal;
	}

	public void setStructsignal(Integer structsignal) {
		this.structsignal = structsignal;
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

	public TypeComputationKind getTck() {
		return tck;
	}

	public void setTck(TypeComputationKind tck) {
		this.tck = tck;
	}

	public boolean isIsonestatementend() {
		return isonestatementend;
	}

	public void setIsonestatementend(boolean isonestatementend) {
		this.isonestatementend = isonestatementend;
	}
	
}