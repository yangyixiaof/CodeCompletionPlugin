package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class argumentList extends OneCode{
	
	private List<referedExpression> el = new LinkedList<referedExpression>();
	
	public argumentList() {
	}
	
	public void AddToFirst(referedExpression re)
	{
		getEl().add(0, re);
	}
	
	public void AddReferedExpression(referedExpression re)
	{
		getEl().add(re);
	}

	public List<referedExpression> getEl() {
		return el;
	}

	public void setEl(List<referedExpression> el) {
		this.el = el;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argumentList)
		{
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			int maxsize = Math.max(size, tsize);
			if (maxsize <= 2)
			{
				return true;
			}
			int minsize = Math.min(size, tsize);
			if (Math.abs(size-tsize) > minsize)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof argumentList)
		{
			int size = el.size();
			int tsize = ((argumentList) t).el.size();
			if (Math.abs(size-tsize) <= 1)
			{
				return 1;
			}
			else
			{
				return SimilarityHelper.ComputeTwoIntegerSimilarity(size, tsize);
			}
		}
		return 0;
	}
	
}