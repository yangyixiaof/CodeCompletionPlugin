package cn.yyx.contentassist.codesynthesis.data;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.commonutils.ComplicatedSignal;

public class CSArrayAccessStartData extends CSFlowLineData{
	
	public CSArrayAccessStartData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
		this.setCsep(dt.getCsep());
		this.setScm(dt.getSynthesisCodeManager());
	}
	
	@Override
	public void HandleStackSignal(Stack<Integer> signals) throws CodeSynthesisException{
		super.HandleStackSignal(signals);
		Integer sl = signals.peek();
		if (sl != null)
		{
			ComplicatedSignal cs = ComplicatedSignal.ParseComplicatedSignal(sl);
			if (cs.getSign() == DataStructureSignalMetaInfo.ArrayAccessBlcok)
			{
				int count = cs.getCount()-1;
				signals.pop();
				if (count > 0)
				{
					cs.setCount(count);
					signals.push(cs.GetSignal());
				}
			}
		}
		throw new CodeSynthesisException("array access does not in right block.");
	}
	
}