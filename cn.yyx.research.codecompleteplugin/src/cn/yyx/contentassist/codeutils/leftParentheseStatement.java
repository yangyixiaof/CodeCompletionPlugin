package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSLeftParenInfoData;
import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class leftParentheseStatement extends statement{
	
	int count = 0;
	
	public leftParentheseStatement(int count) {
		this.count = count;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof leftParentheseStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof leftParentheseStatement)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		cstack.add(ComplicatedSignal.GenerateComplicatedSignal(StructureSignalMetaInfo.ParentheseBlock, count));
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSLeftParenInfoData cpin = new CSLeftParenInfoData(count);
		squeue.add(cpin);
		return false;
	}
	
}