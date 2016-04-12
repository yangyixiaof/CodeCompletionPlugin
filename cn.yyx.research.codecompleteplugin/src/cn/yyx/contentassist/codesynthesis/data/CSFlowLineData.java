package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.flowline.SynthesisCodeManager;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSFlowLineData {
	
	private String id = null;
	private Sentence sete = null;
	private String data = null;
	// private Integer structsignal = null;
	private Class<?> dcls = null;
	private SynthesisHandler handler = null;
	private SynthesisCodeManager scm = new SynthesisCodeManager();
	
	private boolean haspre = false;
	private boolean hashole = false;
	private TypeComputationKind pretck = TypeComputationKind.NoOptr;
	private TypeComputationKind posttck = TypeComputationKind.NoOptr;
	
	private CSExtraData csed = new CSExtraData();
	
	// this boolean field is used to skip some useless node.
	// private boolean shouldskip = false;
	// this boolean field should be set at some specific kind of statement.
	private boolean isonestatementend = false;
	
	public CSFlowLineData(Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHaspre(haspre);
		this.setHashole(hashole);
		this.setPretck(pretck);
		this.setPosttck(posttck);
		this.setHandler(handler);
	}
	
	public CSFlowLineData(String id, Sentence sete, String data, Class<?> dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		this.setId(id + "");
		this.setSete(sete);
		this.setData(data);
		// this.setStructsignal(structsignal);
		this.setDcls(dcls);
		this.setHaspre(haspre);
		this.setHashole(hashole);
		this.setPretck(pretck);
		this.setPosttck(posttck);
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

	public CSExtraData getCsed() {
		return csed;
	}

	public void setCsed(CSExtraData csed) {
		this.csed = csed;
	}

	/*public boolean isShouldskip() {
		return shouldskip;
	}

	public void setShouldskip(boolean shouldskip) {
		this.shouldskip = shouldskip;
	}*/
	
}