package cn.yyx.contentassist.codesynthesis.typeutil;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class TypeConflictException extends CodeSynthesisException {
	
	private static final long serialVersionUID = 1L;
	
	public TypeConflictException(String info) {
		super(info);
	}

}
