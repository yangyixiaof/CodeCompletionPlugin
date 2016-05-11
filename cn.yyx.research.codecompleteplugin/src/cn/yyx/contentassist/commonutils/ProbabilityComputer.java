package cn.yyx.contentassist.commonutils;

public class ProbabilityComputer {
	
	public static double ComputeProbability(double probability)
	{
		if (probability <= 0.00001)
		{
			probability = 0.00001;
		}
		return Math.log(probability);
	}
	
}
