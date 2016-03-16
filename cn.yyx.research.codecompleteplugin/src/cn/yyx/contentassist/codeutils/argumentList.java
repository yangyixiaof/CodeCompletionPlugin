package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		boolean conflict = false;
		StringBuilder fin = new StringBuilder("");
		StringBuilder tsb = new StringBuilder("");
		referedExpression invokerhint = el.get(0);
		AdditionalInfo nai = new AdditionalInfo();
		nai.setFirstInvokerInArgList(true);
		conflict = invokerhint.HandleCodeSynthesis(squeue, handler, tsb, nai);
		if (tsb.length() != 0)
		{
			fin.append(tsb.toString() + ai.getMethodName() + "(");
		}
		List<referedExpression> tl = new LinkedList<referedExpression>();
		Iterator<referedExpression> itr = el.iterator();
		itr.next();
		while (itr.hasNext())
		{
			referedExpression re = itr.next();
			tl.add(0, re);
		}
		// inverse order iterate.
		itr = tl.iterator();
		while (itr.hasNext())
		{
			tsb = new StringBuilder("");
			referedExpression re = itr.next();
			conflict = re.HandleCodeSynthesis(squeue, handler, tsb, null);
			if (conflict)
			{
				return true;
			}
			fin.append(tsb.toString());
			if (itr.hasNext())
			{
				fin.append(",");
			}
		}
		fin.append(")");
		squeue.add(fin.toString());
		return false;
	}
	
}