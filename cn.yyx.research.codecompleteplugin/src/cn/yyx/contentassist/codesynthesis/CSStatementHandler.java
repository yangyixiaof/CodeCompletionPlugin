package cn.yyx.contentassist.codesynthesis;

import cn.yyx.contentassist.codepredict.Sentence;

public class CSStatementHandler {
	
	private Sentence sete = null;
	
	public CSStatementHandler(Sentence sete) {
		this.setSete(sete);
	}

	public Sentence getSete() {
		return sete;
	}

	public void setSete(Sentence sete) {
		this.sete = sete;
	}
	
}