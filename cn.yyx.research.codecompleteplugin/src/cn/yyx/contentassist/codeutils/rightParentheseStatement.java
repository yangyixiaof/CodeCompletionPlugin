package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;

public class rightParentheseStatement extends statement implements CloseBlock {
	
	int count = 0;
	
	public rightParentheseStatement(int count) {
		this.count = count;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof rightParentheseStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer hint = cstack.peek();
		if (hint == null)
		{
			return true;
		}
		ComplicatedSignal cs = ComplicatedSignal.ParseComplicatedSignal(hint);
		if (cs == null || cs.getSign() == StructureSignalMetaInfo.ParentheseBlock || count > cs.getCount())
		{
			return true;
		}
		int remaincounts = cs.getCount() - count;
		if (remaincounts == 0)
		{
			cstack.pop();
		}
		else
		{
			cs.setCount(remaincounts);
		}
		return false;
	}
	
}