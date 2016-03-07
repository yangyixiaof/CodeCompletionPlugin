package cn.yyx.contentassist.codepredict;

public class PredictSequenceManager extends SequenceManager{
	
	public boolean CouldOver(int neededSize) {
		if (GetSize() >= neededSize)
		{
			return true;
		}
		return false;
	}
	
}