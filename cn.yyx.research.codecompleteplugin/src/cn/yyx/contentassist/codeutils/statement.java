package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.flowline.FlowLineStack;

public abstract class statement implements OneCode {
	
	public abstract void HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException;
	
}