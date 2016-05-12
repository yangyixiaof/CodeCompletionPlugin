package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public abstract class statement implements OneCode {
	
	String smtcode = null;
	
	public statement(String smtcode) {
		this.smtcode = smtcode;
	}
	
	public abstract boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException;
	
	@Override
	public String toString() {
		return smtcode;
	}
	
}