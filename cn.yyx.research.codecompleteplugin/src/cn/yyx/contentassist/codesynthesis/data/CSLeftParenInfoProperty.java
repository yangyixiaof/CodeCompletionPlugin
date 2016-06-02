package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;

public class CSLeftParenInfoProperty extends CSExtraProperty {
	
	int times = 0;
	
	public CSLeftParenInfoProperty(int times, CSExtraProperty csepnext) {
		super(csepnext);
		this.times = times;
	}
	
	@Override
	public void HandleStackSignalDetail(Stack<Integer> signals) throws CodeSynthesisException {
		boolean couldstop = false;
		int usetimes = times;
		while (!couldstop)
		{
			if (signals.size() == 0)
			{
				throw new CodeSynthesisException("Right parenthese stack signal handling runs into error.");
			}
			Integer hint = signals.peek();
			if (hint == null)
			{
				throw new CodeSynthesisException("Right parenthese stack signal handling runs into error.");
			}
			ComplicatedSignal cs = ComplicatedSignal.ParseComplicatedSignal(hint);
			if (cs == null || cs.getSign() != DataStructureSignalMetaInfo.ParentheseBlock)
			{
				throw new CodeSynthesisException("Right parenthese stack signal handling runs into error.");
			}
			int remaincounts = Math.max(0, cs.getCount() - usetimes);
			usetimes = (usetimes - (cs.getCount() - remaincounts));
			signals.pop();
			if (remaincounts > 0)
			{
				cs.setCount(remaincounts);
				signals.push(cs.GetSignal());
			}
			if (usetimes == 0)
			{
				couldstop = true;
			}
		}
	}

}