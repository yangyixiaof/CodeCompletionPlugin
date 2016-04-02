package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.statement;

public class CSStamp {
	
	List<CSParLineNode> pars = null;
	
	public CSStamp() {
		pars = new LinkedList<CSParLineNode>();
	}
	
	public boolean ExtendOneStatement(statement smt, List<CSParLineNode> nextpars)
	{
		boolean cft = true;
		Iterator<CSParLineNode> itr = pars.iterator();
		while (itr.hasNext())
		{
			CSParLineNode cs = itr.next();
			CSBackQueue csbq = new CSBackQueue(cs);
			boolean conflict = smt.HandleCodeSynthesis(csbq, nextpars);
			if (!conflict)
			{
				cft = false;
			}
		}
		return cft;
	}
	
}