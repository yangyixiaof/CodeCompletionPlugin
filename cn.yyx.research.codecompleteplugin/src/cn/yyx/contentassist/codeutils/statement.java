package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public abstract class statement implements OneCode {
	
	public abstract boolean HandleOverSignal(Stack<Integer> cstack);
	
}