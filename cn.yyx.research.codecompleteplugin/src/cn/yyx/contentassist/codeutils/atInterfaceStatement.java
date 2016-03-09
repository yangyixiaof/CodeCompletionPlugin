package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class atInterfaceStatement extends statement{
	
	identifier id = null;
	
	public atInterfaceStatement(identifier id) {
		this.id = id;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		// TODO Auto-generated method stub
		
	}
	
}
