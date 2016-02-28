package cn.yyx.research.AeroSpikeHandle;

public class ChooseStrategy {
	
	int strategy = -1;
	
	public static final int MaxProbability = 1;
	public static final int Random = 2;
	
	public boolean IsMaxProbability()
	{
		return strategy == MaxProbability;
	}
	
	public boolean IsRandom()
	{
		return strategy == Random;
	}
	
}