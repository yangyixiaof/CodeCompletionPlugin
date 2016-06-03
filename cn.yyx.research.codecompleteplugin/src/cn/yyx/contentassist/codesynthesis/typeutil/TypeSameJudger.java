package cn.yyx.contentassist.codesynthesis.typeutil;

public class TypeSameJudger {
	
	public static boolean TwoTypesSame(CCType ct1, CCType ct2)
	{
		if (ct1 == null && ct2 == null)
		{
			return true;
		}
		if (ct1 != null && ct2 == null)
		{
			return false;
		}
		if (ct1 == null && ct2 != null)
		{
			return false;
		}
		if (ct1 != null && ct2 != null)
		{
			if (ct1.getCls() == ct2.getCls())
			{
				if (ct1.getCls() == null)
				{
					if (ct1.getClstr().equals(ct2.getClstr()))
					{
						return true;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
}