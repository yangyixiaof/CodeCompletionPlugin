package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.List;

public class classOrInterfaceType extends type{
	
	List<type> tps = null;
	
	public classOrInterfaceType(List<type> result) {
		this.tps = result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			Iterator<type> itr1 = tps.iterator();
			Iterator<type> itr2 = ((classOrInterfaceType) t).tps.iterator();
			while (itr1.hasNext())
			{
				type t1 = itr1.next();
				while (itr2.hasNext())
				{
					type t2 = itr2.next();
					if (t1.CouldThoughtSame(t2))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			double tmax = 0;
			Iterator<type> itr1 = tps.iterator();
			Iterator<type> itr2 = ((classOrInterfaceType) t).tps.iterator();
			while (itr1.hasNext())
			{
				type t1 = itr1.next();
				while (itr2.hasNext())
				{
					type t2 = itr2.next();
					double tsmil = t1.Similarity(t2);
					if (tmax < tsmil)
					{
						tmax = tsmil;
					}
				}
			}
			return tmax;
		}
		return 0;
	}

}
