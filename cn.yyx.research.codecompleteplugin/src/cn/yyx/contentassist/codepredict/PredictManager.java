package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PredictManager {
	
	PriorityQueue<Predict> predicts = new PriorityQueue<Predict>();
	
	public void Merge(PredictManager temppm) {
		// TODO Auto-generated method stub
		
	}

	public void Restrain() {
		// TODO Auto-generated method stub
		
	}
	
	public Iterator<Predict> Iterator()
	{
		return predicts.iterator();
	}
	
	public void AddOnePredict(Predict pd, int maxSize)
	{
		predicts.add(pd);
		if (maxSize > 0)
		{
			int size = predicts.size();
			if (size > maxSize)
			{
				int gap = size - maxSize;
				for (int i=0;i<gap;i++)
				{
					predicts.poll();
				}
			}
		}
	}
	
	public boolean CouldOver(int neededSize) {
		if (predicts.size() >= neededSize)
		{
			return true;
		}
		return false;
	}
	
	public boolean IsEmpty()
	{
		return predicts.size() == 0;
	}
	
}