package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PredictSequenceManager extends SequenceManager {
	
	public boolean CouldOver(int neededSize) {
		if (GetSize() >= neededSize)
		{
			return true;
		}
		return false;
	}

	public List<String> GetAllSynthesisdCodes() {
		List<String> result = new LinkedList<String>();
		Iterator<Sequence> itr = Iterator();
		while (itr.hasNext())
		{
			PredictSequence ps = (PredictSequence) itr.next();
			result.add(ps.GetSynthesisedCode());
		}
		return result;
	}
	
}