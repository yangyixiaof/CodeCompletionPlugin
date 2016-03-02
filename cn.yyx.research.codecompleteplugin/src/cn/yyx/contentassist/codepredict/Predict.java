package cn.yyx.contentassist.codepredict;

import java.util.LinkedList;
import java.util.Queue;

public class Predict implements Comparable<Predict> {
	
	Queue<String> sequence = new LinkedList<String>();

	Double prob = (double) 0;
	
	int kindofpredict;
	
	@Override
	public int compareTo(Predict o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
